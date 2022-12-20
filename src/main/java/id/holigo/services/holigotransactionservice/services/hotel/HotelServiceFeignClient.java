package id.holigo.services.holigotransactionservice.services.hotel;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "holigo-hotel-service")
public interface HotelServiceFeignClient {

    static String DETAIL_TRANSACTION = "/api/v1/hotels/transactions/{id}";

    @RequestMapping(method = RequestMethod.GET, value = DETAIL_TRANSACTION)
    ResponseEntity<Object> getDetailTransaction(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.PUT, value = DETAIL_TRANSACTION)
    ResponseEntity<Object> cancelTransaction(@PathVariable Long id);
}
