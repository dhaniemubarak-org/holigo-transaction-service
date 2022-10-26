package id.holigo.services.holigotransactionservice.services.user;

import id.holigo.services.common.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserServiceFeignClient userServiceFeignClient;

    @Autowired
    public void setUserServiceFeignClient(UserServiceFeignClient userServiceFeignClient) {
        this.userServiceFeignClient = userServiceFeignClient;
    }

    @Override
    public UserDto getUser(Long id) {
        ResponseEntity<UserDto> result = userServiceFeignClient.getCompleteUser(id);
        if (result.getStatusCode().equals(HttpStatus.OK)) {
            return result.getBody();
        }
        return null;
    }
}
