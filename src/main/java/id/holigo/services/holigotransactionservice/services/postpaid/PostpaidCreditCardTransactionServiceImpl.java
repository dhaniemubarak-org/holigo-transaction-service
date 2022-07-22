package id.holigo.services.holigotransactionservice.services.postpaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.IssuedPostpaidCreditcardEvent;
import id.holigo.services.common.model.creditcard.PostpaidCreditcardTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostpaidCreditCardTransactionServiceImpl implements PostpaidCreditCardTransactionService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void issuedTransaction(PostpaidCreditcardTransactionDto postpaidCreditcardTransactionDto) {
        log.info("postpaidCreditcardTransactionDto -> {}", postpaidCreditcardTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_POSTPAID_CC_BY_ID,
                new IssuedPostpaidCreditcardEvent(postpaidCreditcardTransactionDto));
    }

}
