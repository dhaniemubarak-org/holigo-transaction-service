package id.holigo.services.holigotransactionservice.services.postpaid;

import id.holigo.services.common.model.creditcard.PostpaidCreditcardTransactionDto;

public interface PostpaidCreditCardTransactionService {
    void issuedTransaction(PostpaidCreditcardTransactionDto postpaidCreditcardTransactionDto);
}
