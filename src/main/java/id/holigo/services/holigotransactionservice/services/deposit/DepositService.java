package id.holigo.services.holigotransactionservice.services.deposit;

import id.holigo.services.common.model.DepositTransactionDto;

public interface DepositService {

    void issuedDeposit(DepositTransactionDto depositTransactionDto);

    void cancelDeposit(DepositTransactionDto depositTransactionDto);
}
