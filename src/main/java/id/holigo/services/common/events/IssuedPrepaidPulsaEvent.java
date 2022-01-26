package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.pulsa.PrepaidPulsaTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class IssuedPrepaidPulsaEvent implements Serializable{
    
    static final long serialVersionUID = -5616186L;

    private PrepaidPulsaTransactionDto prepaidPulsaTransactionDto;

    public IssuedPrepaidPulsaEvent(PrepaidPulsaTransactionDto prepaidPulsaTransactionDto){
        this.prepaidPulsaTransactionDto = prepaidPulsaTransactionDto;
    }
}
