package id.holigo.services.common.model.gas;

import id.holigo.services.common.model.DeviceTypeEnum;
import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostpaidGasTransactionDto {

    private Long id;

    private String invoiceNumber;

    private UUID transactionId;

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

    private BigDecimal cpAmount;

    private BigDecimal mpAmount;

    private BigDecimal ipAmount;

    private BigDecimal hpAmount;

    private BigDecimal hvAmount;

    private BigDecimal prAmount;

    private BigDecimal ipcAmount;

    private BigDecimal hpcAmount;

    private BigDecimal prcAmount;

    private BigDecimal lossAmount;

    private PaymentStatusEnum paymentStatus;

    private OrderStatusEnum orderStatus;

    private DeviceTypeEnum device;

    private Integer onCheck;

    // Postpaid Transaction End

    private String customerName;

    private String tarif;

    private String standMeter;

    private String period;

    private BigDecimal billAmount;

    private BigDecimal penaltyAmount;

    private String reference;

    private String supplierServiceCode;

    private String supplierProductCode;

    private String supplierTransactionId;

    private String ref1;

    private String ref2;

    private Integer flag = 0;

    private Long userId;

    private BigDecimal striketroughAdmin;
}
