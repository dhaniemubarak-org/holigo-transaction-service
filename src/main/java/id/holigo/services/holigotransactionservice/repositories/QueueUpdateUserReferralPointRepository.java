package id.holigo.services.holigotransactionservice.repositories;

import id.holigo.services.holigotransactionservice.domain.QueueUpdateUserReferralPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueueUpdateUserReferralPointRepository extends JpaRepository<QueueUpdateUserReferralPoint, Long> {

    List<QueueUpdateUserReferralPoint> findAllByHasSent(Boolean value);
}
