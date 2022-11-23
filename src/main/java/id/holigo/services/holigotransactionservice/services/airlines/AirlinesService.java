package id.holigo.services.holigotransactionservice.services.airlines;

import id.holigo.services.common.model.AirlinesTransactionDtoForUser;
import id.holigo.services.common.model.PaymentStatusEnum;

public interface AirlinesService {

    AirlinesTransactionDtoForUser getTransaction(Long id);

    void cancelTransaction(Long id);

    void updatePayment(Long id, PaymentStatusEnum paymentStatus);

    void issuedTransaction(Long id);
}
