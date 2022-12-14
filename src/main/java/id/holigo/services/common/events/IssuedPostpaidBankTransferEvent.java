package id.holigo.services.common.events;

import id.holigo.services.common.model.banktransfer.PostpaidBankTransferTransactionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class IssuedPostpaidBankTransferEvent implements Serializable{
    static final long serialVersionUID = -5512312L;
    private PostpaidBankTransferTransactionDto postpaidBankTransferTransactionDto;

    public IssuedPostpaidBankTransferEvent(PostpaidBankTransferTransactionDto postpaidBankTransferTransactionDto){
        this.postpaidBankTransferTransactionDto = postpaidBankTransferTransactionDto;
    }
}
