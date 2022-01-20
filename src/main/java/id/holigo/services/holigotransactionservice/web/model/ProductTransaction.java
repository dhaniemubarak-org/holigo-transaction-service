package id.holigo.services.holigotransactionservice.web.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTransaction {
    
    private String imageUrl;
    private String nominalSelected;
    private String description;
    private BigDecimal price;
}
