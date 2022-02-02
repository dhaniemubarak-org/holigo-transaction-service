package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.multifinance.PostpaidMultifinanceTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class IssuedPostpaidMultifinanceEvent implements Serializable{
    static final long serialVersionUID = -871231512L;
    
    private PostpaidMultifinanceTransactionDto postpaidMultifinanceTransactionDto;

    public IssuedPostpaidMultifinanceEvent(PostpaidMultifinanceTransactionDto postpaidMultifinanceTransactionDto){
        this.postpaidMultifinanceTransactionDto = postpaidMultifinanceTransactionDto;
    }
}
