package id.holigo.services.common.events;

import id.holigo.services.common.model.gas.PostpaidGasTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class IssuedPostpaidGasEvent implements Serializable{
    
    static final long serialVersionUID = -51231412L;

    private PostpaidGasTransactionDto postpaidGasTransactionDto;

    public IssuedPostpaidGasEvent(PostpaidGasTransactionDto postpaidGasTransactionDto){
        this.postpaidGasTransactionDto = postpaidGasTransactionDto;
    }
}
