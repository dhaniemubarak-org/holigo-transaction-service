package id.holigo.services.holigotransactionservice.services.payment;

import id.holigo.services.common.model.TransactionDto;

public interface PaymentService {
    void transactionIssued(TransactionDto transactionDto);
}
