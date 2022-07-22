package id.holigo.services.holigotransactionservice.services.postpaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.IssuedPostpaidElectricitiesEvent;
import id.holigo.services.common.model.electricities.PostpaidElectricitiesTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostpaidElectricitiesTransactionServiceImpl implements PostpaidElectricitiesTransactionService {

    private JmsTemplate jmsTemplate;
    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void issuedTransaction(PostpaidElectricitiesTransactionDto postpaidElectricitiesTransactionDto) {
        log.info("issuedTransaction is running...");
        log.info("postpaidElectricitiesTransactionDto -> {}", postpaidElectricitiesTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_POSTPAID_ELECTRICITIES_BY_ID,
                new IssuedPostpaidElectricitiesEvent(postpaidElectricitiesTransactionDto));
    }

}
