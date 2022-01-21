package id.holigo.services.holigotransactionservice.services.prepaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.model.electricities.PrepaidElectricitiesTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PrepaidElectricitiesTransactionServiceImpl implements PrepaidElectricitiesTransactionService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void issuedTransaction(PrepaidElectricitiesTransactionDto prepaidElectricitiesTransactionDto) {
        log.info("issuedTransaction is running...");
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_PREPAID_ELECTRICITIES_BY_ID, prepaidElectricitiesTransactionDto);
    }

}
