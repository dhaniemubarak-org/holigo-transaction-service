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

    private QueueUpdateUserReferralPointRepository queueUpdateUserReferralPointRepository;

    @Autowired
    public void setQueueUpdateUserReferralPointRepository(QueueUpdateUserReferralPointRepository queueUpdateUserReferralPointRepository) {
        this.queueUpdateUserReferralPointRepository = queueUpdateUserReferralPointRepository;
    }

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
    public void updatePointReferral(Long id, Integer point) {
        try {
            UserReferralDto userReferralDto = UserReferralDto.builder()
                    .point(point)
                    .build();
            ResponseEntity<HttpStatus> response = userServiceFeignClient.putUserPointReferral(id, userReferralDto);

            if (response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
                throw new Exception(Objects.requireNonNull(response.getBody()).toString());
            }
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage());
            queueUpdateUserReferralPointRepository.save(QueueUpdateUserReferralPoint.builder()
                    .userId(id).hasSent(false).point(point).build());
        }
    }
}
