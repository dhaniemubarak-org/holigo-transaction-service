package id.holigo.services.holigotransactionservice.services.postpaid;

import id.holigo.services.common.model.telephone.PostpaidTelephoneTransactionDto;

public interface PostpaidTelephoneTranasctionService {
    void issuedTransaction(PostpaidTelephoneTransactionDto postpaidTelephoneTransactionDto);
}
