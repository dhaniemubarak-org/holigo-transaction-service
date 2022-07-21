package id.holigo.services.holigotransactionservice.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> findByIdAndPaymentStatus(UUID id, PaymentStatusEnum paymentStatus);

    Optional<Transaction> findByTransactionIdAndTransactionType(String transactionId, String transactionType);

    Page<Transaction> findAllByUserId(Long userId, Pageable pageable);

    List<Transaction> findAllByPaymentStatusIn(List<PaymentStatusEnum> paymentStatuses);
}
