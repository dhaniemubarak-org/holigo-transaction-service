package id.holigo.services.holigotransactionservice.services.user;

import id.holigo.services.common.model.UserDto;
import id.holigo.services.common.model.UserReferralDto;
import id.holigo.services.holigotransactionservice.domain.QueueUpdateUserReferralPoint;
import id.holigo.services.holigotransactionservice.repositories.QueueUpdateUserReferralPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
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

    @Override
    public ResponseEntity<HttpStatus> updatePointReferral(Long id, Integer point) {
        UserReferralDto userReferralDto = UserReferralDto.builder()
                .point(point)
                .build();
        return userServiceFeignClient.putUserPointReferral(id, id, userReferralDto);
    }
}
