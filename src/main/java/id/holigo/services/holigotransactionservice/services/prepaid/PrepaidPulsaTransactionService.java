package id.holigo.services.holigotransactionservice.services.prepaid;

import id.holigo.services.common.model.pulsa.PrepaidPulsaTransactionDto;

public interface PrepaidPulsaTransactionService {
    void issuedTransaction(PrepaidPulsaTransactionDto prepaidElectricitiesTransactionDto);
}
