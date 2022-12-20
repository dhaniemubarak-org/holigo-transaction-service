package id.holigo.services.holigotransactionservice.services.user;

import id.holigo.services.common.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserDto getUser(Long id);

    ResponseEntity<HttpStatus> updatePointReferral(Long id, Integer point);
}
