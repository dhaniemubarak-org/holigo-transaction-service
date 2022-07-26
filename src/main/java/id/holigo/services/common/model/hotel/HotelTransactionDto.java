package id.holigo.services.common.model.hotel;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelTransactionDto implements Serializable {
    static final long serialVersionUID = -5123123L;

    private Long id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    private Long userId;

    private Integer serviceId;

    private Long hotelId;

    private Long roomId;

    private BigDecimal fareAmount;

    private BigDecimal discountAmount;

    private BigDecimal nraAmount;

    private BigDecimal ntaAmount;

    private BigDecimal totalEstimationPrice;

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

    private String selectedID;

    private String selectedIDRoom;

    private String supplierTransactionId;

    private String voucherNumber;

    private String resNumber;

    private String guestTitle;

    private String guestName;

    private String guestPhoneNumber;

    private String guestNote;

    private OrderStatusEnum orderStatus;

    private PaymentStatusEnum paymentStatus;

    private Short flag = 0;

    private Date checkIn;

    private Date checkOut;

    private Short roomAmount;

    private Short guestAmount;

    private UUID transactionId;
}
