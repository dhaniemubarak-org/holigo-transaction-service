package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.telephone.PostpaidTelephoneTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class IssuedPostpaidTelephoneEvent implements Serializable{
    static final long serialVersionUID = -871231512L;
    
    private PostpaidTelephoneTransactionDto postpaidTelephoneTransactionDto;

    public IssuedPostpaidTelephoneEvent(PostpaidTelephoneTransactionDto postpaidTelephoneTransactionDto){
        this.postpaidTelephoneTransactionDto = postpaidTelephoneTransactionDto;
    }
}
