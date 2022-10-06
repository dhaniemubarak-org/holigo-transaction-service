package id.holigo.services.holigotransactionservice.config;

import java.util.EnumSet;
import java.util.UUID;

import id.holigo.services.common.model.PaymentDto;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.services.PaymentStatusTransactionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.events.PaymentStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableStateMachineFactory(name = "paymentStatusTransactionSMF")
@Configuration
public class PaymentTransactionSMConfig extends StateMachineConfigurerAdapter<PaymentStatusEnum, PaymentStatusEvent> {
    private final TransactionRepository transactionRepository;

    private final KafkaTemplate<String, PaymentDto> paymentKafkaTemplate;

    @Override
    public void configure(StateMachineStateConfigurer<PaymentStatusEnum, PaymentStatusEvent> states) throws Exception {
        states.withStates().initial(PaymentStatusEnum.SELECTING_PAYMENT)
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
                .event(PaymentStatusEvent.PAYMENT_PAID)
                .and().withExternal().source(PaymentStatusEnum.SELECTING_PAYMENT).target(PaymentStatusEnum.PAYMENT_EXPIRED)
                .event(PaymentStatusEvent.PAYMENT_EXPIRED).action(paymentExpiredAction())
                .and().withExternal().source(PaymentStatusEnum.WAITING_PAYMENT).target(PaymentStatusEnum.PAYMENT_EXPIRED)
                .event(PaymentStatusEvent.PAYMENT_EXPIRED).action(paymentExpiredAction())
                .and().withExternal().source(PaymentStatusEnum.SELECTING_PAYMENT).target(PaymentStatusEnum.PAYMENT_CANCELED)
                .event(PaymentStatusEvent.PAYMENT_CANCEL).action(paymentCanceledAction())
                .and().withExternal().source(PaymentStatusEnum.WAITING_PAYMENT).target(PaymentStatusEnum.PAYMENT_CANCELED)
                .event(PaymentStatusEvent.PAYMENT_CANCEL).action(paymentCanceledAction());
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

    @Bean
    public Action<PaymentStatusEnum, PaymentStatusEvent> paymentExpiredAction() {
        return stateContext -> {
            Transaction transaction = transactionRepository.getById(UUID.fromString(
                    stateContext.getMessageHeader(PaymentStatusTransactionServiceImpl.PAYMENT_STATUS_HEADER).toString()));
            if (transaction.getPaymentId() != null) {
                paymentKafkaTemplate.send(KafkaTopicConfig.UPDATE_PAYMENT, PaymentDto.builder()
                        .id(transaction.getPaymentId())
                        .status(PaymentStatusEnum.PAYMENT_EXPIRED).build());
            }
        };
    }

    @Bean
    public Action<PaymentStatusEnum, PaymentStatusEvent> paymentCanceledAction() {
        return stateContext -> {
            Transaction transaction = transactionRepository.getById(UUID.fromString(
                    stateContext.getMessageHeader(PaymentStatusTransactionServiceImpl.PAYMENT_STATUS_HEADER).toString()));
            if (transaction.getPaymentId() != null) {
                paymentKafkaTemplate.send(KafkaTopicConfig.CANCEL_PAYMENT, PaymentDto.builder().id(transaction.getPaymentId()).build());
            }
        };
    }
}
