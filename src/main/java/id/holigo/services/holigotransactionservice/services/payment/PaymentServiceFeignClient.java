package id.holigo.services.holigotransactionservice.services.payment;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import id.holigo.services.common.model.PaymentDtoForUser;

@FeignClient(name = "holigo-payment-service")
public interface PaymentServiceFeignClient {

    String PAYMENT_DETAIL_BY_ID = "/api/v1/payments/{id}";

    @RequestMapping(method = RequestMethod.GET, value = PAYMENT_DETAIL_BY_ID)
    ResponseEntity<PaymentDtoForUser> getPayment(@PathVariable UUID id);

}
