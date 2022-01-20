package id.holigo.services.holigotransactionservice.listeners;

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
}
