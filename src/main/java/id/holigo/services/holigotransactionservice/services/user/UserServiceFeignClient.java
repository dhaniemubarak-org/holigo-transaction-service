package id.holigo.services.holigotransactionservice.services.user;

import id.holigo.services.common.model.UserDto;
import id.holigo.services.common.model.UserReferralDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "holigo-user-service")
public interface UserServiceFeignClient {
    String USER_PATH = "/api/v1/completeUsers/{id}";

    String USER_POINT_REFERRAL_PATH = "/api/v1/users/{id}/userReferral";

    @RequestMapping(method = RequestMethod.GET, value = USER_PATH)
    ResponseEntity<UserDto> getCompleteUser(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.PUT, value = USER_POINT_REFERRAL_PATH)
    ResponseEntity<HttpStatus> putUserPointReferral(@PathVariable("id") Long id, @RequestHeader("user-id") Long userId, @RequestBody UserReferralDto userReferralDto);
}
