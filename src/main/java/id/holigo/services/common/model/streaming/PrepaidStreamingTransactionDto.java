package id.holigo.services.common.model.streaming;

import id.holigo.services.common.model.DeviceTypeEnum;
import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrepaidStreamingTransactionDto implements Serializable {
    static final long serialVersionUID = -44123L;

    private Long id;

    private String invoiceNumber;

    private UUID transactionId;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    private String serialNumber;

    private String reference;

    private String supplierTransactionId;

    private String supplierServiceCode;

    private String supplierProductCode;

    private String supplierNominalCode;

    private Integer serviceId;

    private Integer productId;

    private Integer nominalId;

    private String customerNumber;

    private BigDecimal adminAmount;

    private BigDecimal fareAmount;

    private BigDecimal discountAmount;

    private BigDecimal ntaAmount;

    private BigDecimal nraAmount;

    private BigDecimal cpAmount;

    private BigDecimal pointAmount;

    private BigDecimal mpAmount;

    private BigDecimal ipAmount;

    private BigDecimal hpAmount;

    private BigDecimal hvAmount;

    private BigDecimal prAmount;

    private BigDecimal ipcAmount;

    private BigDecimal hpcAmount;

    private BigDecimal prcAmount;

    private BigDecimal lossAmount;

    private String ref1;

    private String ref2;

    private DeviceTypeEnum device;

    private OrderStatusEnum orderStatus;

    private PaymentStatusEnum paymentStatus;

    private Integer onCheck;

    private Integer flag=0;

    private Long userId;
}
