package id.holigo.services.holigotransactionservice.services.postpaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.IssuedPostpaidTvInternetEvent;
import id.holigo.services.common.model.netv.PostpaidTvInternetTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostpaidTvInternetTransactionServiceImpl implements PostpaidTvInternetTransactionService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void issuedTransaction(PostpaidTvInternetTransactionDto postpaidTvInternetTransactionDto) {
        log.info("issuedTransaction postpaidTvInternetTransactionDto is running dto -> {}",
                postpaidTvInternetTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_POSTPAID_NETV_BY_ID,
                new IssuedPostpaidTvInternetEvent(postpaidTvInternetTransactionDto));

    }

}
