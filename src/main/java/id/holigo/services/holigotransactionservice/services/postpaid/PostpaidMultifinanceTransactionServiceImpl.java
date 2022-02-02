package id.holigo.services.holigotransactionservice.services.postpaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.IssuedPostpaidMultifinanceEvent;
import id.holigo.services.common.model.multifinance.PostpaidMultifinanceTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostpaidMultifinanceTransactionServiceImpl implements PostpaidMultifinanceTransactionService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void issuedTransaction(PostpaidMultifinanceTransactionDto postpaidMultifinanceTransactionDto) {
        log.info("issuedTransaction PostpaidMultifinanceTransactionDto is running dto -> {}",
                postpaidMultifinanceTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_POSTPAID_MULTIFINANCE_BY_ID,
                new IssuedPostpaidMultifinanceEvent(postpaidMultifinanceTransactionDto));

    }

}
