package id.holigo.services.holigotransactionservice.services.postpaid;

import id.holigo.services.common.model.insurance.PostpaidInsuranceTransactionDto;

public interface PostpaidInsuranceTransactionService {

    void issuedTransaction(PostpaidInsuranceTransactionDto postpaidInsuranceTransactionDto);
}
