package id.holigo.services.common.model.electricities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import id.holigo.services.common.model.DeviceTypeEnum;
import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrepaidElectricitiesTransactionDto implements Serializable {

    private static final long serialVersionUID = 1234L;

    private Long id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    // Section Electricities
    private String customerName;

    private String tariffAdjustment;

    private BigDecimal nominalAmount;

    private BigDecimal adminAmount;

    private String kwh;

    private BigDecimal rupiahToken;

    private BigDecimal ppn;

    private BigDecimal ppj;

    private BigDecimal materai;

    private String token;

    private String ref;

    private String supplierTransactionId;

    private String supplierServiceCode;

    private String supplierProductCode;

    // Section Transaction

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

    private Integer flag;
}
