package id.holigo.services.holigotransactionservice.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Transaction {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(length = 36, columnDefinition = "varchar(36)", nullable = true)
    private UUID parentId;

    @Column(nullable = true)
    private String invoiceNumber;

    private Long userId;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(nullable = true)
    private Timestamp deletedAt;

    @Column(nullable = true)
    private Timestamp expiredAt;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal discountAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal fareAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal ntaAmount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal nraAmount;

    private Integer serviceId;

    private Integer productId;

    @Column(name = "index_user")
    private String indexUser;

    @Lob
    @Column(name = "index_commission")
    private String indexCommission;

    @Lob
    @Column(name = "index_product")
    private String indexProduct;

    private String transactionId;

    private String transactionType;

    @Column(length = 36, columnDefinition = "varchar(36)", nullable = true)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    private String note;

}
