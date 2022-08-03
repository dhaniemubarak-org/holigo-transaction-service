package id.holigo.services.holigotransactionservice.services.airlines;

import id.holigo.services.common.model.AirlinesTransactionDtoForUser;

public interface AirlinesService {

    AirlinesTransactionDtoForUser getTransaction(Long id);

    void cancelTransaction(Long id);
}
