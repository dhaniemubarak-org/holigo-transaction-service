package id.holigo.services.common.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailProductTransaction {

    private Integer id;
    private String imageUrl;
    private String name;
    private String nominalSelected;
    private String description;
    private BigDecimal price;

}
