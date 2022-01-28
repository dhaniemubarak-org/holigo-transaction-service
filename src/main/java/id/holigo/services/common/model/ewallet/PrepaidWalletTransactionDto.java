package id.holigo.services.common.model.ewallet;

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

public class PrepaidWalletTransactionDto implements Serializable{

    static final long serialVersionUID = -756123123L;
    
    private Long id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    private String serialNumber;

    private String supplierTransactionId;

    private String supplierServiceCode;

    private String supplierProductCode;

    private String supplierNominalCode;

    private Integer serviceId;

    private Integer productId;

    private Integer nominalId;

    private String customerNumber;

    private BigDecimal fareAmount;

    private BigDecimal discountAmount;

    private BigDecimal markupAmount;

    private BigDecimal ntaAmount;

    private BigDecimal nraAmount;

    private DeviceTypeEnum device;

    private OrderStatusEnum orderStatus;

    private PaymentStatusEnum paymentStatus;

    private Integer onCheck;

    private UUID transactionId;
}
