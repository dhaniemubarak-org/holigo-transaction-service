package id.holigo.services.holigotransactionservice.services.prepaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.IssuedPrepaidGameEvent;
import id.holigo.services.common.model.games.PrepaidGameTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PrepaidGameTransactionServiceImpl implements PrepaidGameTransactionService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void issuedTransaction(PrepaidGameTransactionDto prepaidGameTransactionDto) {
        log.info("issuedTransaction is running...");
        log.info("prepaidGameTransactionDto -> {}", prepaidGameTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_PREPAID_GAMES_BY_ID,
                new IssuedPrepaidGameEvent(prepaidGameTransactionDto));
    }

}
