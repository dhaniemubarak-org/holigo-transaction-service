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
import id.holigo.services.common.model.electricities.PrepaidElectricitiesTransactionDto;
import id.holigo.services.common.model.electricities.PostpaidElectricitiesTransactionDto;
import id.holigo.services.common.model.pulsa.PrepaidPulsaTransactionDto;
import id.holigo.services.common.model.games.PrepaidGameTransactionDto;
import id.holigo.services.common.model.ewallet.PrepaidWalletTransactionDto;
import id.holigo.services.common.model.netv.PostpaidTvInternetTransactionDto;
import id.holigo.services.common.model.telephone.PostpaidTelephoneTransactionDto;
import id.holigo.services.common.model.insurance.PostpaidInsuranceTransactionDto;
import id.holigo.services.common.model.multifinance.PostpaidMultifinanceTransactionDto;
import id.holigo.services.common.model.creditcard.PostpaidCreditcardTransactionDto;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.events.OrderStatusEvent;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.services.OrderStatusTransactionServiceImpl;
import id.holigo.services.holigotransactionservice.services.postpaid.PostpaidCreditCardTransactionService;
import id.holigo.services.holigotransactionservice.services.postpaid.PostpaidElectricitiesTransactionService;
import id.holigo.services.holigotransactionservice.services.postpaid.PostpaidInsuranceTransactionService;
import id.holigo.services.holigotransactionservice.services.postpaid.PostpaidMultifinanceTransactionService;
import id.holigo.services.holigotransactionservice.services.postpaid.PostpaidPdamTransactionService;
import id.holigo.services.holigotransactionservice.services.postpaid.PostpaidTelephoneTranasctionService;
import id.holigo.services.holigotransactionservice.services.postpaid.PostpaidTvInternetTransactionService;
import id.holigo.services.holigotransactionservice.services.prepaid.PrepaidElectricitiesTransactionService;
import id.holigo.services.holigotransactionservice.services.prepaid.PrepaidGameTransactionService;
import id.holigo.services.holigotransactionservice.services.prepaid.PrepaidPulsaTransactionService;
import id.holigo.services.holigotransactionservice.services.prepaid.PrepaidWalletTransactionService;
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
    private final PostpaidElectricitiesTransactionService postpaidElectricitiesTransactionService;

    @Autowired
    private final PrepaidGameTransactionService prepaidGameTransactionService;

    @Autowired
    private final PrepaidWalletTransactionService prepaidWalletTransactionService;

    @Autowired
    private final PostpaidTvInternetTransactionService postpaidTvInternetTransactionService;

    @Autowired
    private final PostpaidTelephoneTranasctionService postpaidTelephoneTranasctionService;

    @Autowired
    private final PostpaidInsuranceTransactionService postpaidInsuranceTransactionService;

    @Autowired
    private final PostpaidMultifinanceTransactionService postpaidMultifinanceTransactionService;

    @Autowired
    private final PostpaidCreditCardTransactionService postpaidCreditCardTransactionService;

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
                case "PAS":
                    log.info("Issued Pascabayar is running...");
                    PostpaidElectricitiesTransactionDto postpaidElectricitiesTransactionDto = PostpaidElectricitiesTransactionDto
                            .builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).orderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    postpaidElectricitiesTransactionService.issuedTransaction(postpaidElectricitiesTransactionDto);
                    break;
                case "GAME":
                    log.info("Issued game is running...");
                    PrepaidGameTransactionDto prepaidGameTransactionDto = PrepaidGameTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).orderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    prepaidGameTransactionService.issuedTransaction(prepaidGameTransactionDto);
                    break;
                case "EWAL":
                case "DWAL":
                    log.info("Issued EWAL / DWAL is running...");
                    PrepaidWalletTransactionDto prepaidWalletTransactionDto = PrepaidWalletTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).orderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    prepaidWalletTransactionService.issuedTranasction(prepaidWalletTransactionDto);
                    break;
                case "NETV":
                    log.info("Issued NETV / DWAL is running...");
                    PostpaidTvInternetTransactionDto postpaidTvInternetTransactionDto = PostpaidTvInternetTransactionDto
                            .builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).orderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    postpaidTvInternetTransactionService.issuedTransaction(postpaidTvInternetTransactionDto);
                    break;
                case "TLP":
                    log.info("Issued TLP is running...");
                    PostpaidTelephoneTransactionDto postpaidTelephoneTransactionDto = PostpaidTelephoneTransactionDto
                            .builder().id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).orderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    postpaidTelephoneTranasctionService.issuedTransaction(postpaidTelephoneTransactionDto);
                    break;
                case "INS":
                    log.info("Issued INS is running...");
                    PostpaidInsuranceTransactionDto postpaidInsuranceTransactionDto = PostpaidInsuranceTransactionDto
                            .builder().id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).orderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    postpaidInsuranceTransactionService.issuedTransaction(postpaidInsuranceTransactionDto);
                    break;
                case "MFN":
                    log.info("Issued MFN is running...");
                    PostpaidMultifinanceTransactionDto postpaidMultifinanceTransactionDto = PostpaidMultifinanceTransactionDto
                            .builder().id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).orderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    postpaidMultifinanceTransactionService.issuedTransaction(postpaidMultifinanceTransactionDto);
                    break;
                case "CC":
                    log.info("Issued CC is running...");
                    PostpaidCreditcardTransactionDto postpaidCreditcardTransactionDto = PostpaidCreditcardTransactionDto
                            .builder().id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(transaction.getPaymentStatus()).orderStatus(transaction.getOrderStatus())
                            .transactionId(transaction.getId()).build();
                    postpaidCreditCardTransactionService.issuedTransaction(postpaidCreditcardTransactionDto);
                    break;
            }
        };
    }
}
