package id.holigo.services.holigotransactionservice.services.payment;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import id.holigo.services.common.events.TransactionEvent;
import id.holigo.services.common.model.PaymentDtoForUser;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private final JmsTemplate jmsTemplate;

    private final PaymentServiceFeignClient paymentServiceFeignClient;

    @Override
    public void transactionIssued(TransactionDto transactionDto) {
        log.info("transactionIssued is running...");
        jmsTemplate.convertAndSend(JmsConfig.UPDATE_PAYMENT_STATUS_BY_PAYMENT_ID, new TransactionEvent(transactionDto));
    }

    @Override
    public PaymentDtoForUser getPayment(UUID id) {

        ResponseEntity<PaymentDtoForUser> responseEntity = paymentServiceFeignClient.getPayment(id);

        return responseEntity.getBody();
    }

}
