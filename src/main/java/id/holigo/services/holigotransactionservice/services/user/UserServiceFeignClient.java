package id.holigo.services.holigotransactionservice.services.user;

import id.holigo.services.common.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "holigo-user-service")
public interface UserServiceFeignClient {
    String USER_PATH = "/api/v1/completeUsers/{id}";

    @RequestMapping(method = RequestMethod.GET, value = USER_PATH)
    ResponseEntity<UserDto> getCompleteUser(@PathVariable("id") Long id);
}
