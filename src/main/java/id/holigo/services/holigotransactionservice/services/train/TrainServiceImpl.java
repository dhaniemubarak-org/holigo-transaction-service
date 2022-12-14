package id.holigo.services.holigotransactionservice.services.train;

import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.common.model.TrainTransactionDtoForUser;
import id.holigo.services.holigotransactionservice.config.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TrainServiceImpl implements TrainService {

    private TrainServiceFeignClient trainServiceFeignClient;

    private final KafkaTemplate<String, TrainTransactionDtoForUser> trainKafkaTemplate;

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
    public void updatePayment(Long id, PaymentStatusEnum paymentStatusEnum) {
        trainKafkaTemplate.send(KafkaTopicConfig.UPDATE_PAYMENT_STATUS_TRAIN_TRANSACTION, TrainTransactionDtoForUser.builder()
                .id(id)
                .paymentStatus(paymentStatusEnum)
                .build());
    }

    @Override
    public void issuedTransaction(Long id) {
        trainKafkaTemplate.send(KafkaTopicConfig.UPDATE_PAYMENT_STATUS_TRAIN_TRANSACTION, TrainTransactionDtoForUser.builder()
                .id(id)
                .paymentStatus(PaymentStatusEnum.PAID)
                .build());
    }
}
