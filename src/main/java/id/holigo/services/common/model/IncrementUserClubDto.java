package id.holigo.services.common.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncrementUserClubDto implements Serializable {

    static final long serialVersionUID = 89234567L;

    private String invoiceNumber;

    private Long userId;

    private BigDecimal fareAmount;
}
