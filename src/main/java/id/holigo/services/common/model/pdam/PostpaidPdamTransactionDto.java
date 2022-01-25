package id.holigo.services.common.model.pdam;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostpaidPdamTransactionDto {
    
    private Long id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    private Integer serviceId;

    private Integer productId;

    private Integer billQty;

    private String customerNumber;

    private BigDecimal fareAmount;

    private BigDecimal discountAmount;

    private BigDecimal adminAmount;

    private BigDecimal ntaAmount;

    private BigDecimal nraAmount;

    private PaymentStatusEnum paymentStatus;

    private OrderStatusEnum OrderStatus;

    private String device;

    private String detailableType;

    private String detailableId;

    private Integer onCheck;

    private String customerName;

    private String period;

    private BigDecimal billAmount;

    private String reference;

    private String supplierServiceCode;

    private String supplierProductCode;

    private String supplierTransactionId;

    private UUID transactionId;
}
