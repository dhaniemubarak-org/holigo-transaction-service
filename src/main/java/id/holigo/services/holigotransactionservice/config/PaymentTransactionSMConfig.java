package id.holigo.services.holigotransactionservice.config;

import java.util.EnumSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.events.PaymentStatusEvent;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.services.OrderStatusTransactionService;
import id.holigo.services.holigotransactionservice.services.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableStateMachineFactory(name = "paymentStatusTransactionSMF")
@Configuration
public class PaymentTransactionSMConfig extends StateMachineConfigurerAdapter<PaymentStatusEnum, PaymentStatusEvent> {

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final OrderStatusTransactionService orderStatusTransactionService;

    @Override
    public void configure(StateMachineStateConfigurer<PaymentStatusEnum, PaymentStatusEvent> states) throws Exception {
        states.withStates().initial(PaymentStatusEnum.WAITING_PAYMENT)
                .states(EnumSet.allOf(PaymentStatusEnum.class))
                .end(PaymentStatusEnum.PAYMENT_FAILED)
                .end(PaymentStatusEnum.PAYMENT_CANCELED)
                .end(PaymentStatusEnum.PAYMENT_EXPIRED)
                .end(PaymentStatusEnum.REFUNDED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<PaymentStatusEnum, PaymentStatusEvent> transitions)
            throws Exception {
        transitions.withExternal().source(PaymentStatusEnum.WAITING_PAYMENT).target(PaymentStatusEnum.PAID)
                .action(paidAction())
                .event(PaymentStatusEvent.PAYMENT_PAID);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<PaymentStatusEnum, PaymentStatusEvent> config)
            throws Exception {
        StateMachineListenerAdapter<PaymentStatusEnum, PaymentStatusEvent> adapter = new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<PaymentStatusEnum, PaymentStatusEvent> from,
                    State<PaymentStatusEnum, PaymentStatusEvent> to) {
                log.info(String.format("stateChange(from: %s, to %s)", from.getId(), to.getId()));
            }
        };
        config.withConfiguration().listener(adapter);
    }

    public Action<PaymentStatusEnum, PaymentStatusEvent> paidAction() {
        return context -> {
            log.info("paidAction running in PaymentTranscationSMConfig .....");
            Transaction transaction = transactionRepository.getById(
                    UUID.fromString(context.getMessageHeader(TransactionServiceImpl.TRANSACTION_HEADER).toString()));

            orderStatusTransactionService.processIssued(transaction.getId());
        };
    }
}
