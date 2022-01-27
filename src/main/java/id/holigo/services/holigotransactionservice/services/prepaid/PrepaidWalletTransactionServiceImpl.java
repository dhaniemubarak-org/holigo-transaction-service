package id.holigo.services.holigotransactionservice.services.prepaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.IssuedPrepaidWalletEvent;
import id.holigo.services.common.model.ewallet.PrepaidWalletTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PrepaidWalletTransactionServiceImpl implements PrepaidWalletTransactionService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void issuedTranasction(PrepaidWalletTransactionDto prepaidWalletTransactionDto) {
        log.info("issuedTransaction is running...");
        log.info("prepaidWalletTransactionDto -> {}", prepaidWalletTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_PREPAID_WALLET_BY_ID,
                new IssuedPrepaidWalletEvent(prepaidWalletTransactionDto));
    }

}
