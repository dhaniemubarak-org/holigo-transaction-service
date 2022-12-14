package id.holigo.services.holigotransactionservice.services.train;

import id.holigo.services.common.model.TrainTransactionDtoForUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "holigo-train-service")
public interface TrainServiceFeignClient {

    String TRAIN_PATH = "/api/v1/train/transactions/{id}";

    @RequestMapping(method = RequestMethod.GET, value = TRAIN_PATH)
    ResponseEntity<TrainTransactionDtoForUser> getTransaction(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.PUT, value = TRAIN_PATH)
    ResponseEntity<TrainTransactionDtoForUser> cancelTransaction(@PathVariable Long id);
}
