package id.holigo.services.holigotransactionservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;

@SpringBootTest
public class PaymentStatusTransactionServiceImplTest {

    Transaction transaction;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PaymentStatusTransactionService paymentStatusTransactionService;

    @Autowired
    OrderStatusTransactionService orderStatusTransactionService;

    @BeforeEach
    void setUp() {
        transaction = Transaction.builder().id(UUID.fromString("1ef35204-a505-455a-894a-b1b5cdf43b44")).userId(5L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now())).updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .discountAmount(new BigDecimal(0.00)).expiredAt(Timestamp.valueOf(LocalDateTime.now()))
                .fareAmount(new BigDecimal(98500.00)).nraAmount(new BigDecimal(500.00)).ntaAmount(new BigDecimal(98000))
                .productId(10).serviceId(13).orderStatus(OrderStatusEnum.PROCESS_BOOK)
                .paymentStatus(PaymentStatusEnum.SELECTING_PAYMENT).transactionId("77763746").transactionType("PLNPRE")
                .build();
    }

    @Transactional
    @Test
    void testTransactionHasBeenPaid() {
        Transaction savedTransaction = transactionRepository.save(transaction);

        assertEquals(PaymentStatusEnum.SELECTING_PAYMENT, savedTransaction.getPaymentStatus());

        orderStatusTransactionService.bookingSuccess(savedTransaction.getId());

        Transaction bookingTransaction = transactionRepository.getById(savedTransaction.getId());
        assertEquals(OrderStatusEnum.BOOKED, bookingTransaction.getOrderStatus());

        // paymentStatusTransactionService.transactionHasBeenPaid(savedTransaction.getId());

        // Transaction paidTransaction = transactionRepository.getById(savedTransaction.getId());

        // assertEquals(OrderStatusEnum.PROCESS_ISSUED,
        //         paidTransaction.getOrderStatus());

    }
}
