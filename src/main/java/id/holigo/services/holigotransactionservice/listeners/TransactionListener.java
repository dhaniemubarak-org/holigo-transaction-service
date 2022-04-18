package id.holigo.services.holigotransactionservice.listeners;

import java.util.Optional;

import javax.jms.JMSException;
import javax.jms.Message;

import com.netflix.discovery.converters.Auto;
import id.holigo.services.common.model.IncrementUserClubDto;
import id.holigo.services.common.model.UpdateUserPointDto;
import id.holigo.services.holigotransactionservice.services.holiclub.HoliclubService;
import id.holigo.services.holigotransactionservice.services.point.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import id.holigo.services.common.events.TransactionEvent;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.services.OrderStatusTransactionService;
import id.holigo.services.holigotransactionservice.services.PaymentStatusTransactionService;
import id.holigo.services.holigotransactionservice.services.TransactionService;
import id.holigo.services.holigotransactionservice.services.payment.PaymentService;
import id.holigo.services.holigotransactionservice.web.mappers.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionListener {

    @Autowired
    private final JmsTemplate jmsTemplate;

    @Autowired
    private final TransactionService transactionService;

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final TransactionMapper transactionMapper;

    @Autowired
    private final OrderStatusTransactionService orderStatusTransactionService;

    @Autowired
    private final PaymentStatusTransactionService paymentStatusTransactionService;

    @Autowired
    private final PaymentService paymentService;

    @Autowired
    private final HoliclubService holiclubService;

    @Autowired
    private final PointService pointService;

    @Transactional
    @JmsListener(destination = JmsConfig.CREATE_NEW_TRANSACTION)
    public void listenForCreateNewTransaction(@Payload TransactionDto transactionDto, @Headers MessageHeaders headers,
                                              Message message) throws JmsException, JMSException {

        TransactionDto transaction = transactionService.createNewTransaction(transactionDto);

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), transaction);
    }

    @JmsListener(destination = JmsConfig.GET_TRANSACTION_BY_ID)
    public void listenForGetTransaction(@Payload TransactionDto transactionDto, @Headers MessageHeaders headers,
                                        Message message) throws JmsException, JMSException {
        log.info("listen for get transaction ....");
        TransactionDto transaction = transactionService.getTransactionById(transactionDto.getId());
        log.info("transaction -> {}", transaction);
        if (transaction != null) {
            transactionDto = transaction;
        }
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), transactionDto);
    }

    @Transactional
    @JmsListener(destination = JmsConfig.SET_ORDER_STATUS_BY_TRANSACTION_ID_TYPE)
    public void listenForSetOrderStatusTransaction(TransactionEvent transactionEvent) {
        log.info("listenForSetOrderStatusTransaction is running ....");
        log.info("transactionDto -> {}",
                transactionEvent.getTransactionDto());

        TransactionDto transactionDto = transactionEvent.getTransactionDto();

        Optional<Transaction> fetchTransaction = transactionRepository.findById(transactionDto.getId());
        if (fetchTransaction.isPresent()) {
            log.info("transaction found");
            Transaction transaction = fetchTransaction.get();
            TransactionDto transactionDtoForPayment = transactionMapper
                    .transactionToTransactionDto(transaction);
            transactionDtoForPayment.setOrderStatus(transactionDto.getOrderStatus());
            switch (transactionDto.getOrderStatus()) {
                case ISSUED:
                    log.info("Switch to ISSUED");
                    orderStatusTransactionService.issuedSuccess(transaction.getId());
                    paymentService.transactionIssued(transactionDtoForPayment);
                    IncrementUserClubDto incrementUserClubDto = IncrementUserClubDto.builder()
                            .invoiceNumber(transaction.getInvoiceNumber()).userId(transaction.getUserId())
                            .fareAmount(transaction.getFareAmount()).build();
                    holiclubService.incrementUserClub(incrementUserClubDto);
                    UpdateUserPointDto updateUserPointDto = UpdateUserPointDto.builder()
                            .userId(transaction.getUserId()).credit(0).debit(transaction.getHpAmount().intValue())
                            .invoiceNumber(transaction.getInvoiceNumber()).build();
                    pointService.updateUserPoint(updateUserPointDto);
                    break;
                case ISSUED_FAILED:
                    log.info("Switch to ISSUED_FAILED");
                    orderStatusTransactionService.issuedFail(transaction.getId());
                    paymentService.transactionIssued(transactionDtoForPayment);
                    break;
                case RETRYING_ISSUED:
                    log.info("Switch to RETRYING_ISSUED");
                    orderStatusTransactionService.retryingIssued(transaction.getId());
                    paymentService.transactionIssued(transactionDtoForPayment);
                    break;
                case WAITING_ISSUED:
                    log.info("Switch to WAITING_ISSUED");
                    orderStatusTransactionService.waitingIssued(transaction.getId());
                    paymentService.transactionIssued(transactionDtoForPayment);
                    break;
                case PROCESS_BOOK:
                case BOOKED:
                case BOOK_FAILED:
                case PROCESS_ISSUED:
                case ORDER_EXPIRED:
                case ORDER_CANCELED:
                    break;

            }
        } else {
            log.info("transaction not found");
        }

    }

    @Transactional
    @JmsListener(destination = JmsConfig.ISSUED_TRANSACTION_BY_ID)
    public void listenForIssuedFromPayment(TransactionEvent transactionEvent) {
        log.info("Listening for issued from payment...");
        log.info("transactionDto -> {}", transactionEvent.getTransactionDto());
        TransactionDto transactionDto = transactionEvent.getTransactionDto();
        Optional<Transaction> fetchTransaction = transactionRepository.findByIdAndPaymentStatus(transactionDto.getId(),
                transactionDto.getPaymentStatus());
        if (fetchTransaction.isPresent()) {
            log.info("Transaction found");
            Transaction transaction = fetchTransaction.get();
            paymentStatusTransactionService.transactionHasBeenPaid(transaction.getId());
            orderStatusTransactionService.processIssued(transaction.getId());
        } else {
            log.info("Transaction not found");
        }
    }

    @Transactional
    @JmsListener(destination = JmsConfig.SET_PAYMENT_IN_TRANSACTION_BY_ID)
    public void listenForSetPayment(TransactionEvent transactionEvent) {
        log.info("Listening for set payment...");
        log.info("transactionDto -> {}", transactionEvent.getTransactionDto());
        TransactionDto transactionDto = transactionEvent.getTransactionDto();
        Optional<Transaction> fetchTransaction = transactionRepository.findById(transactionDto.getId());
        if (fetchTransaction.isPresent()) {
            Transaction transaction = fetchTransaction.get();
            transaction.setPaymentId(transactionDto.getPaymentId());
            transaction.setPaymentStatus(transactionDto.getPaymentStatus());
            transaction.setPointAmount(transactionDto.getPointAmount());
            transaction.setPaymentServiceId(transactionDto.getPaymentServiceId());
            transaction.setVoucherCode(transactionDto.getVoucherCode());
            transactionRepository.save(transaction);
        }
    }
}
