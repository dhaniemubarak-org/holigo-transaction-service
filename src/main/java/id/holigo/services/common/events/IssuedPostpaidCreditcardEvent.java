package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.creditcard.PostpaidCreditcardTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder

public class IssuedPostpaidCreditcardEvent implements Serializable{
    
    static final long serialVersionUID = -5512312L;

    private PostpaidCreditcardTransactionDto postpaidCreditcardTransactionDto;

    public IssuedPostpaidCreditcardEvent(PostpaidCreditcardTransactionDto postpaidCreditcardTransactionDto){
        this.postpaidCreditcardTransactionDto = postpaidCreditcardTransactionDto;
    }
}
