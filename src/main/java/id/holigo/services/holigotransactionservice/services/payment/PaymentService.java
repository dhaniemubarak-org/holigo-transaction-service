package id.holigo.services.holigotransactionservice.services.payment;

import java.util.UUID;

import id.holigo.services.common.model.PaymentDtoForUser;
import id.holigo.services.common.model.TransactionDto;

public interface PaymentService {
    void transactionIssued(TransactionDto transactionDto);

    PaymentDtoForUser getPayment(UUID id);
}
