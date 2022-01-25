package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.pdam.PostpaidPdamTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class IssuedPostpaidPdamEvent implements Serializable{
    
    static final long serialVersionUID = -51231412L;

    private PostpaidPdamTransactionDto postpaidPdamTransactionDto;

    public IssuedPostpaidPdamEvent(PostpaidPdamTransactionDto postpaidPdamTransactionDto){
        this.postpaidPdamTransactionDto = postpaidPdamTransactionDto;
    }
}
