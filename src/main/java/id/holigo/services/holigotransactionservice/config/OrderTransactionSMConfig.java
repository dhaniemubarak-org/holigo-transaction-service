package id.holigo.services.holigotransactionservice.config;

import java.util.EnumSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.common.model.electricities.PrepaidElectricitiesTransactionDto;
import id.holigo.services.common.model.pulsa.PrepaidPulsaTransactionDto;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.events.OrderStatusEvent;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.services.OrderStatusTransactionServiceImpl;
import id.holigo.services.holigotransactionservice.services.payment.PaymentService;
import id.holigo.services.holigotransactionservice.services.postpaid.PostpaidPdamTransactionService;
import id.holigo.services.holigotransactionservice.services.prepaid.PrepaidElectricitiesTransactionService;
import id.holigo.services.holigotransactionservice.services.prepaid.PrepaidPulsaTransactionService;
import id.holigo.services.holigotransactionservice.web.mappers.TransactionMapper;
import id.holigo.services.common.model.pdam.PostpaidPdamTransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableStateMachineFactory(name = "orderStatusTransactionSMF")
public class OrderTransactionSMConfig extends StateMachineConfigurerAdapter<OrderStatusEnum, OrderStatusEvent> {

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final PrepaidElectricitiesTransactionService prepaidElectricitiesTransactionService;

    @Autowired
    private final PostpaidPdamTransactionService postpaidPdamTransactionService;

    @Autowired
    private final PrepaidPulsaTransactionService prepaidPulsaTransactionService;

    @Autowired
    private final PaymentService paymentService;

    private final TransactionMapper transactionMapper;

    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderStatusEvent> states) throws Exception {
        states.withStates().initial(OrderStatusEnum.PROCESS_BOOK)
                .states(EnumSet.allOf(OrderStatusEnum.class))
                .end(OrderStatusEnum.BOOK_FAILED)
                .end(OrderStatusEnum.ISSUED)
                .end(OrderStatusEnum.ISSUED_FAILED)
                .end(OrderStatusEnum.ORDER_CANCELED)
                .end(OrderStatusEnum.ORDER_EXPIRED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderStatusEvent> transitions)
            throws Exception {
        transitions.withExternal().source(OrderStatusEnum.PROCESS_BOOK).target(OrderStatusEnum.BOOKED)
                .event(OrderStatusEvent.BOOK_SUCCESS)
                .and()
                .withExternal().source(OrderStatusEnum.PROCESS_BOOK).target(OrderStatusEnum.BOOK_FAILED)
                .event(OrderStatusEvent.BOOK_FAIL)
                .and()
                .withExternal().source(OrderStatusEnum.BOOKED).target(OrderStatusEnum.PROCESS_ISSUED)
                .action(processIssued())
                .event(OrderStatusEvent.PROCESS_ISSUED)
                .and()
                .withExternal().source(OrderStatusEnum.PROCESS_ISSUED).target(OrderStatusEnum.ISSUED)
                .action(issuedSuccess())
                .event(OrderStatusEvent.ISSUED_SUCCESS)
                .and()
                .withExternal().source(OrderStatusEnum.PROCESS_ISSUED).target(OrderStatusEnum.ISSUED_FAILED)
                .event(OrderStatusEvent.ISSUED_FAIL)
                .and()
                .withExternal().source(OrderStatusEnum.PROCESS_ISSUED).target(OrderStatusEnum.WAITING_ISSEUD)
                .event(OrderStatusEvent.WAITING_ISSUED)
                .and()
                .withExternal().source(OrderStatusEnum.PROCESS_ISSUED).target(OrderStatusEnum.RETRYING_ISSUED)
                .event(OrderStatusEvent.RETRYING_ISSUED)
                .and()
                .withExternal().source(OrderStatusEnum.BOOKED).target(OrderStatusEnum.ORDER_CANCELED)
                .event(OrderStatusEvent.BOOK_CANCEL)
                .and()
                .withExternal().source(OrderStatusEnum.BOOKED).target(OrderStatusEnum.ORDER_EXPIRED)
                .event(OrderStatusEvent.BOOK_EXPIRE);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatusEnum, OrderStatusEvent> config)
            throws Exception {
        StateMachineListenerAdapter<OrderStatusEnum, OrderStatusEvent> adapter = new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<OrderStatusEnum, OrderStatusEvent> from,
                    State<OrderStatusEnum, OrderStatusEvent> to) {
                log.info(String.format("stateChange(from: %s, to %s)", from.getId(), to.getId()));
            }
        };
        config.withConfiguration().listener(adapter);
    }

    public Action<OrderStatusEnum, OrderStatusEvent> processIssued() {
        return context -> {
            Transaction transaction = transactionRepository.getById(UUID.fromString(
                    context.getMessageHeader(OrderStatusTransactionServiceImpl.TRANSACTION_HEADER).toString()));

            log.info("Transaction before send JMS -> {}", transaction);
            switch (transaction.getTransactionType()) {
                case "PRA":
                    PrepaidElectricitiesTransactionDto prepaidElectricitiesTransactionDto = PrepaidElectricitiesTransactionDto
                            .builder().id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).orderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    prepaidElectricitiesTransactionService.issuedTransaction(prepaidElectricitiesTransactionDto);
                    break;
                case "PAM":
                    log.info("Issued PAM is running....");
                    PostpaidPdamTransactionDto postpaidPdamTransactionDto = PostpaidPdamTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).OrderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    postpaidPdamTransactionService.issuedTransaction(postpaidPdamTransactionDto);
                    break;
                case "PUL":
                case "PD":
                case "PR":
                    log.info("Issued Pulsa is running....");
                    PrepaidPulsaTransactionDto prepaidPulsaTransactionDto = PrepaidPulsaTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).orderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    prepaidPulsaTransactionService.issuedTransaction(prepaidPulsaTransactionDto);
                    break;
            }
        };
    }

    public Action<OrderStatusEnum, OrderStatusEvent> issuedSuccess() {
        log.info("issuedSuccess is running...");
        return context -> {
            Transaction transaction = transactionRepository.getById(UUID.fromString(
                    context.getMessageHeader(OrderStatusTransactionServiceImpl.TRANSACTION_HEADER).toString()));
            TransactionDto transactionDto = transactionMapper.transactionToTransactionDto(transaction);
            paymentService.transactionIssued(transactionDto);

        };
    }
}