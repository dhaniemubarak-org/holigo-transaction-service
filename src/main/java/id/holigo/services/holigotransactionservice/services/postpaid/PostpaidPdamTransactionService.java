package id.holigo.services.holigotransactionservice.services.postpaid;

import id.holigo.services.common.model.pdam.PostpaidPdamTransactionDto;

public interface PostpaidPdamTransactionService {
    void issuedTransaction(PostpaidPdamTransactionDto postpaidPdamTransactionDto);
}
