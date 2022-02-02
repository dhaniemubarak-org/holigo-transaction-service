package id.holigo.services.holigotransactionservice.services.postpaid;

import id.holigo.services.common.model.netv.PostpaidTvInternetTransactionDto;

public interface PostpaidTvInternetTransactionService {
    void issuedTransaction(PostpaidTvInternetTransactionDto postpaidTvInternetTransactionDto);
}
