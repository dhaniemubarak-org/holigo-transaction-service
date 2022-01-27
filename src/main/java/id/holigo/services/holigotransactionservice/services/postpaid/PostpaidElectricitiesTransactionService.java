package id.holigo.services.holigotransactionservice.services.postpaid;

import id.holigo.services.common.model.electricities.PostpaidElectricitiesTransactionDto;

public interface PostpaidElectricitiesTransactionService {
    void issuedTransaction(PostpaidElectricitiesTransactionDto postpaidElectricitiesTransactionDto);
}
