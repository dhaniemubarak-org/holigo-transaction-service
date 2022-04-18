package id.holigo.services.common.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserPointDto implements Serializable {
    private Long userId;

    private Integer credit;

    private Integer debit;

    private String invoiceNumber;

    private String indexNote;

    private String noteValue;
}
