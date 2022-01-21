package id.holigo.services.holigotransactionservice.services.prepaid;

import id.holigo.services.common.model.electricities.PrepaidElectricitiesTransactionDto;

public interface PrepaidElectricitiesTransactionService {
    void issuedTransaction(PrepaidElectricitiesTransactionDto prepaidElectricitiesTransactionDto);
}
