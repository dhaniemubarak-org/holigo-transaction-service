package id.holigo.services.holigotransactionservice.services.postpaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.IssuedPostpaidInsuranceEvent;
import id.holigo.services.common.model.insurance.PostpaidInsuranceTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostpaidInsuranceTransactionServiceImpl implements PostpaidInsuranceTransactionService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void issuedTransaction(PostpaidInsuranceTransactionDto postpaidInsuranceTransactionDto) {
        log.info("issuedTransaction PostpaidInsuranceTransactionDto is running dto -> {}",
                postpaidInsuranceTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_POSTPAID_INS_BY_ID,
                new IssuedPostpaidInsuranceEvent(postpaidInsuranceTransactionDto));
    }

}
