package id.holigo.services.holigotransactionservice.listeners;

import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.config.KafkaTopicConfig;
import id.holigo.services.holigotransactionservice.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class KafkaTransactionListener {

    private final TransactionService transactionService;

    private final TransactionTemplate transactionTemplate;

    @KafkaListener(topics = KafkaTopicConfig.UPDATE_DATA_SUBSIDY_AP_SUPPLIER_TRANSACTION, groupId = "update-data-transaction", containerFactory = "updateDataTransactionDtoListenerContainerFactory")
    void listen(TransactionDto transactionDto){
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                transactionService.updateDataSubsidyApSupplierTransaction(transactionDto);
            }
        });
    }
}
