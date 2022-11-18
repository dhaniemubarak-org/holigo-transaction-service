package id.holigo.services.holigotransactionservice.services.train;

import id.holigo.services.common.model.TrainTransactionDtoForUser;

public interface TrainService {

    TrainTransactionDtoForUser getTransaction(Long id);

    void cancelTransaction(Long id);

    void issuedTransaction(Long id);
}
