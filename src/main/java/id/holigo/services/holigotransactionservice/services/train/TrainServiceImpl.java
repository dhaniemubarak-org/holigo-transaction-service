package id.holigo.services.holigotransactionservice.services.train;

import id.holigo.services.common.model.TrainTransactionDtoForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TrainServiceImpl implements TrainService {

    private TrainServiceFeignClient trainServiceFeignClient;

    @Autowired
    public void setTrainServiceFeignClient(TrainServiceFeignClient trainServiceFeignClient) {
        this.trainServiceFeignClient = trainServiceFeignClient;
    }

    @Override
    public TrainTransactionDtoForUser getTransaction(Long id) {
        ResponseEntity<TrainTransactionDtoForUser> response = trainServiceFeignClient.getTransaction(id);
        return response.getBody();
    }

    @Override
    public void cancelTransaction(Long id) {
        ResponseEntity<TrainTransactionDtoForUser> response = trainServiceFeignClient.cancelTransaction(id);
    }

    @Override
    public void issuedTransaction(Long id) {
        // TODO ISSUED TRAIN
    }
}
