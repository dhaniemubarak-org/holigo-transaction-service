package id.holigo.services.holigotransactionservice.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigotransactionservice.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> findByTransactionIdAndTransactionType(String transactionId, String transactionType);
}
