package id.holigo.services.common.model.telephone;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import id.holigo.services.common.model.DeviceTypeEnum;
import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PostpaidTelephoneTransactionDto implements Serializable{
    static final long serialVersionUID = -6512312L;
    
    private Long id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    // @Column(nullable = false)
    // private Long userId;

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

    private OrderStatusEnum orderStatus;

    private DeviceTypeEnum device;

    private String detailableType;

    private String detailableId;

    private Integer onCheck;

    // Postpaid Transaction End

    private String customerName;

    private BigDecimal billAmount;

    private String period;

    private String reference;

    private String supplierServiceCode;

    private String supplierProductCode;

    private String supplierTransactionId;

    private UUID transactionId;
}
