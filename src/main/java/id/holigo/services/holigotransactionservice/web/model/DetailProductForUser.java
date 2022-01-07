package id.holigo.services.holigotransactionservice.web.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class DetailProductForUser {
    
    private String message;
    @JsonIgnore
    private Boolean status;
    private String name;
    private String imageUrl;
    private String nominalSelected;
    private String description;
    private BigDecimal price;
}
