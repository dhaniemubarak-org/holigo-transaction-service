package id.holigo.services.common.model.banktransfer;

import id.holigo.services.common.model.DeviceTypeEnum;
import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostpaidBankTransferTransactionDto {
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    // @Column(nullable = false)
    // private Long userId;
    private Integer serviceId;
    private Integer productId;
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
    private String customerNumber;
    private String customerName;
    private BigDecimal nominalTransfer;
    private String reference;
    private String supplierServiceCode;
    private String supplierProductCode;
    private String supplierTransactionId;
    private Integer flag = 0;
    private String invoiceNumber;
    private UUID transactionId;
    private String ref1;
    private String ref2;
    private Long userId;
    private BigDecimal striketroughAdmin;
}
