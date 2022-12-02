package id.holigo.services.holigotransactionservice.services.train;

import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.common.model.TrainTransactionDtoForUser;

public interface TrainService {

    TrainTransactionDtoForUser getTransaction(Long id);

    void cancelTransaction(Long id);

    void updatePayment(Long id, PaymentStatusEnum paymentStatusEnum);

    void issuedTransaction(Long id);
}
