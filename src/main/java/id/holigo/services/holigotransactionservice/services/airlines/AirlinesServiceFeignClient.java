package id.holigo.services.holigotransactionservice.services.airlines;

import id.holigo.services.common.model.AirlinesTransactionDtoForUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "holigo-airlines-service")
public interface AirlinesServiceFeignClient {

    String AIRLINES_PATH = "/api/v1/airlines/transactions/{id}";

    @RequestMapping(method = RequestMethod.GET, value = AIRLINES_PATH)
    ResponseEntity<AirlinesTransactionDtoForUser> getTransaction(@PathVariable Long id);
}
