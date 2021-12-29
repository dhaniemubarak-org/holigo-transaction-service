package id.holigo.services.holigotransactionservice.web.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import id.holigo.services.common.model.StatusOrderEnum;
import id.holigo.services.common.model.StatusPaymentEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDtoForUser {
    
    private UUID id;

    private String shortId;

    private Timestamp createdAt;

    private Timestamp expiredAt;

    private BigDecimal discountAmount;

    private BigDecimal fareAmount;

    private BigDecimal ntaAmount;

    private BigDecimal nraAmount;

    private String indexProduct;

    private String transactionId;

    private String transactionType;

    private UUID paymentId;

    private StatusPaymentEnum statusPayment;

    private StatusOrderEnum statusOrder;
}
