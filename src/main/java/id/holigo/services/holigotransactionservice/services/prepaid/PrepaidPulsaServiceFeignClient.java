package id.holigo.services.holigotransactionservice.services.prepaid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "holigo-prepaid-pulsa-service")
public interface PrepaidPulsaServiceFeignClient {

    static String DETAIL_TRANSACTION = "/api/v1/prepaid/pulsa/transactions/{id}";

    @RequestMapping(method = RequestMethod.GET, value = DETAIL_TRANSACTION)
    ResponseEntity<Object> getDetailTransaction(@PathVariable Long id, @RequestHeader("Accept-Language") String locale);

    @RequestMapping(method = RequestMethod.GET, value = DETAIL_TRANSACTION)
    ResponseEntity<Object> cancelTransaction(@PathVariable Long id);
}
