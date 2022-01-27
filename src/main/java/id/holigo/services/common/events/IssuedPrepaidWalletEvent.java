package id.holigo.services.common.events;

import id.holigo.services.common.model.ewallet.PrepaidWalletTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder

public class IssuedPrepaidWalletEvent {

    static final long serialVersionUID = -512312L;

    private PrepaidWalletTransactionDto prepaidWalletTransactionDto;

    public IssuedPrepaidWalletEvent(PrepaidWalletTransactionDto prepaidWalletTransactionDto) {
        this.prepaidWalletTransactionDto = prepaidWalletTransactionDto;
    }
}
