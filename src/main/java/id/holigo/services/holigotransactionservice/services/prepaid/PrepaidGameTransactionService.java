package id.holigo.services.holigotransactionservice.services.prepaid;

import id.holigo.services.common.model.games.PrepaidGameTransactionDto;

public interface PrepaidGameTransactionService {
    void issuedTransaction(PrepaidGameTransactionDto prepaidGameTransactionDto);
}
