package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.insurance.PostpaidInsuranceTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class IssuedPostpaidInsuranceEvent implements Serializable{
    
    static final long serialVersionUID = -44123122L;

    private PostpaidInsuranceTransactionDto postpaidInsuranceTransactionDto;

    public IssuedPostpaidInsuranceEvent(PostpaidInsuranceTransactionDto postpaidInsuranceTransactionDto){
        this.postpaidInsuranceTransactionDto = postpaidInsuranceTransactionDto;
    }

}
