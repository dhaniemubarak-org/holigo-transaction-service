package id.holigo.services.holigotransactionservice.services.prepaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.IssuedPrepaidPulsaEvent;
import id.holigo.services.common.model.pulsa.PrepaidPulsaTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PrepaidPulsaTransactionServiceImpl implements PrepaidPulsaTransactionService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void issuedTransaction(PrepaidPulsaTransactionDto prepaidPulsaTransactionDto) {
        log.info("issuedTransaction is running...");
        log.info("prepaidPulsaTransactionDto -> {}", prepaidPulsaTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_PREPAID_PULSA_BY_ID,
                new IssuedPrepaidPulsaEvent(prepaidPulsaTransactionDto));
    }

}
