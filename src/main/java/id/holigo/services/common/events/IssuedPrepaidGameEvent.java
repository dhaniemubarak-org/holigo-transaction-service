package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.games.PrepaidGameTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class IssuedPrepaidGameEvent implements Serializable{
    
    static final long serialVersionUID = -5616186L;

    private PrepaidGameTransactionDto prepaidGameTransactionDto;

    public IssuedPrepaidGameEvent(PrepaidGameTransactionDto prepaidGameTransactionDto){
        this.prepaidGameTransactionDto = prepaidGameTransactionDto;
    }
}
