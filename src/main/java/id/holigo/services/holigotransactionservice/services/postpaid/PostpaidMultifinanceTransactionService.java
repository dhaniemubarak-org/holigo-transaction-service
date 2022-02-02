package id.holigo.services.holigotransactionservice.services.postpaid;

import id.holigo.services.common.model.multifinance.PostpaidMultifinanceTransactionDto;

public interface PostpaidMultifinanceTransactionService {
    void issuedTransaction(PostpaidMultifinanceTransactionDto postpaidMultifinanceTransactionDto);
}
