package id.holigo.services.holigotransactionservice.services.airlines;

import id.holigo.services.common.model.AirlinesTransactionDtoForUser;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.config.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AirlinesServiceImpl implements AirlinesService {

    private final AirlinesServiceFeignClient airlinesServiceFeignClient;

    private final KafkaTemplate<String, AirlinesTransactionDtoForUser> airlinesKafkaTemplate;

    @Override
    public AirlinesTransactionDtoForUser getTransaction(Long id) {
        ResponseEntity<AirlinesTransactionDtoForUser> responseEntity = airlinesServiceFeignClient.getTransaction(id);
        return responseEntity.getBody();
    }

    @Override
    public void cancelTransaction(Long id) {
        airlinesServiceFeignClient.cancelTransaction(id);
    }

    @Override
    public void updatePayment(Long id, PaymentStatusEnum paymentStatus) {
        airlinesKafkaTemplate.send(KafkaTopicConfig.UPDATE_PAYMENT_STATUS_AIRLINES_TRANSACTION, AirlinesTransactionDtoForUser.builder()
                .id(id)
                .paymentStatus(paymentStatus)
                .build());
    }


    @Override
    public void issuedTransaction(Long id) {
        airlinesKafkaTemplate.send(KafkaTopicConfig.UPDATE_PAYMENT_STATUS_AIRLINES_TRANSACTION, AirlinesTransactionDtoForUser.builder()
                .id(id)
                .paymentStatus(PaymentStatusEnum.PAID)
                .build());
    }

}
