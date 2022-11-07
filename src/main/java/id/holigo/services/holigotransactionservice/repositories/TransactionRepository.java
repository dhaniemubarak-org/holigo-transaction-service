package id.holigo.services.holigotransactionservice.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {

    Optional<Transaction> findByIdAndPaymentStatus(UUID id, PaymentStatusEnum paymentStatus);

    Optional<Transaction> findByTransactionIdAndTransactionType(String transactionId, String transactionType);

    Page<Transaction> findAllByUserId(Long userId, Pageable pageable);

    List<Transaction> findAllByPaymentStatusInAndExpiredAtLessThanEqual(List<PaymentStatusEnum> paymentStatuses, Timestamp timestamp);

    List<Transaction> findAllByPaymentServiceIdAndPaymentStatus(String paymentServiceId, PaymentStatusEnum paymentStatusEnum);
}
