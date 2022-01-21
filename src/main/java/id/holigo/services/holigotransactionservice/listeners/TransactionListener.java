package id.holigo.services.holigotransactionservice.listeners;

import java.util.Optional;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.services.OrderStatusTransactionService;
import id.holigo.services.holigotransactionservice.services.TransactionService;
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
    private final OrderStatusTransactionService orderStatusTransactionService;

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

    @JmsListener(destination = JmsConfig.SET_ORDER_STATUS_TRANSACTION_BY_TRANSACTION_ID)
    public void listenForSetOrderStatusTransaction(TransactionDto transactionDto) {
        log.info("listenForSetOrderStatusTransaction is running ....");

        Optional<Transaction> fetchTransaction = transactionRepository.findByTransactionIdAndTransactionType(
                transactionDto.getTransactionId(), transactionDto.getTransactionType());
        if (fetchTransaction.isPresent()) {
            Transaction transaction = fetchTransaction.get();
            switch (transaction.getOrderStatus().toString()) {
                case "ISSUED":
                    orderStatusTransactionService.issuedSuccess(transaction.getId());
                    break;
                case "ISSUED_FAILED":
                    orderStatusTransactionService.issuedFail(transaction.getId());
                    break;
            }
        }

    }
}
