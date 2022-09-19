package id.holigo.services.holigotransactionservice.services.deposit;

import id.holigo.services.common.model.DepositTransactionDto;
import id.holigo.services.holigotransactionservice.config.KafkaTopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DepositServiceImpl implements DepositService {

    private KafkaTemplate<String, DepositTransactionDto> depositTransactionKafkaTemplate;

    @Autowired
    public void setDepositTransactionKafkaTemplate(KafkaTemplate<String, DepositTransactionDto> depositTransactionKafkaTemplate) {
        this.depositTransactionKafkaTemplate = depositTransactionKafkaTemplate;
    }

    @Override
    public void issuedDeposit(DepositTransactionDto depositTransactionDto) {
        depositTransactionKafkaTemplate.send(KafkaTopicConfig.UPDATE_DEPOSIT_TRANSACTION, depositTransactionDto);
    }
}
