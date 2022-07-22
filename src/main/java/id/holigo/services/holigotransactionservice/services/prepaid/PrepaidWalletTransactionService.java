package id.holigo.services.holigotransactionservice.services.prepaid;

import id.holigo.services.common.model.ewallet.PrepaidWalletTransactionDto;

public interface PrepaidWalletTransactionService {
    void issuedTransaction(PrepaidWalletTransactionDto prepaidWalletTransactionDto);
}
