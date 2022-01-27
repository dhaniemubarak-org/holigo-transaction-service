package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.electricities.PostpaidElectricitiesTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class IssuedPostpaidElectricitiesEvent implements Serializable{
    
    static final long serialVersionUID = -312451231L;

    private PostpaidElectricitiesTransactionDto postpaidElectricitiesTransactionDto;

    public IssuedPostpaidElectricitiesEvent(PostpaidElectricitiesTransactionDto postpaidElectricitiesTransactionDto){
        this.postpaidElectricitiesTransactionDto = postpaidElectricitiesTransactionDto;
    }

}
