package id.holigo.services.common.model.banktransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailTransactionBankTransferDto {
    @Builder.Default
    private Boolean isPostpaid=true;
    private String parentCode;
    private String serviceCode;
    private String serviceName;
    private String productCode;
    private String productName;
    private String productImageUrl;
    @JsonProperty("serviceImageUrl")
    private String imageUrl;
    private String customerNumber;
    private String customerName;
    private BigDecimal adminAmount;
    private BigDecimal nominalTransfer;
    private OrderStatusEnum orderStatus;
    private PaymentStatusEnum paymentStatus;
    private BigDecimal striketroughAdmin;
}
