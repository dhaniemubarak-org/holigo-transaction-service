package id.holigo.services.holigotransactionservice.services.postpaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.IssuedPostpaidPdamEvent;
import id.holigo.services.common.model.pdam.PostpaidPdamTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostpaidPdamTransactionServiceImpl implements PostpaidPdamTransactionService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void issuedTransaction(PostpaidPdamTransactionDto postpaidPdamTransactionDto) {
        log.info("issuedTransaction PostpaidPdamTransactionDto is running dto -> {}", postpaidPdamTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_POSTPAID_PDAM_BY_ID,
                new IssuedPostpaidPdamEvent(postpaidPdamTransactionDto));
    }

}
