package id.holigo.services.holigotransactionservice.services.postpaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.IssuedPostpaidTelephoneEvent;
import id.holigo.services.common.model.telephone.PostpaidTelephoneTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostpaidTelephoneTransactionServiceImpl implements PostpaidTelephoneTranasctionService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void issuedTransaction(PostpaidTelephoneTransactionDto postpaidTelephoneTransactionDto) {
        log.info("issuedTransaction postpaidTelephoneTransactionDto isrunning dto -> {}",
                postpaidTelephoneTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_POSTPAID_TLP_BY_ID,
                new IssuedPostpaidTelephoneEvent(postpaidTelephoneTransactionDto));
    }

}
