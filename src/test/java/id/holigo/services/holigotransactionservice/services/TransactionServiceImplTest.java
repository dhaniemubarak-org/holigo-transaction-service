package id.holigo.services.holigotransactionservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.common.model.TransactionDto;

@SpringBootTest
public class TransactionServiceImplTest {

    @Autowired
    TransactionService transactionService;

    TransactionDto transactionDto;

    @BeforeEach
    void setUp() {
        transactionDto = TransactionDto.builder().id(UUID.fromString("1ef35204-a505-455a-894a-b1b5cdf43b44")).userId(5L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now())).updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .discountAmount(new BigDecimal(0.00)).expiredAt(Timestamp.valueOf(LocalDateTime.now()))
                .fareAmount(new BigDecimal(98500.00)).nraAmount(new BigDecimal(500.00)).ntaAmount(new BigDecimal(98000))
                .productId(10).serviceId(13).transactionId("77763746").transactionType("PRA").build();
    }

    @Transactional
    @Test
    void testCreateNewTransaction() {
        TransactionDto savedTransactionDto = transactionService.createNewTransaction(transactionDto);

        assertEquals(PaymentStatusEnum.SELECTING_PAYMENT, savedTransactionDto.getPaymentStatus());

        assertEquals(OrderStatusEnum.BOOKED, savedTransactionDto.getOrderStatus());

        String invoiceNumber = savedTransactionDto.getServiceId().toString()
                + "/" + savedTransactionDto.getCreatedAt().toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString()
                + "/" + savedTransactionDto.getTransactionId();
        assertEquals(invoiceNumber, savedTransactionDto.getInvoiceNumber());

    }
}
