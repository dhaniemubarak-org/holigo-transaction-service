package id.holigo.services.holigotransactionservice.schedulers;

import feign.FeignException;
import id.holigo.services.holigotransactionservice.domain.QueueUpdateUserReferralPoint;
import id.holigo.services.holigotransactionservice.repositories.QueueUpdateUserReferralPointRepository;
import id.holigo.services.holigotransactionservice.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PointReferral {

    private UserService userService;

    private QueueUpdateUserReferralPointRepository updateUserReferralPointRepository;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUpdateUserReferralPointRepository(QueueUpdateUserReferralPointRepository updateUserReferralPointRepository) {
        this.updateUserReferralPointRepository = updateUserReferralPointRepository;
    }

    @Scheduled(fixedDelay = 300000)
    public void updatePointReferral() {
        List<QueueUpdateUserReferralPoint> queueUpdateUserReferralPoints = updateUserReferralPointRepository.findAllByHasSent(false);
        queueUpdateUserReferralPoints.forEach(queueUpdateUserReferralPoint -> {
            try {
                ResponseEntity<HttpStatus> response = userService.updatePointReferral(queueUpdateUserReferralPoint.getUserId(), queueUpdateUserReferralPoint.getPoint());
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    queueUpdateUserReferralPoint.setHasSent(true);
                    updateUserReferralPointRepository.save(queueUpdateUserReferralPoint);
                }
            } catch (FeignException e) {
                log.error("Error -> {}", e.getMessage());
            }

        });
    }
}
