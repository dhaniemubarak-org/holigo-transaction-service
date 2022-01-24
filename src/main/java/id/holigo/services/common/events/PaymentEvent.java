package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.PaymentDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class PaymentEvent implements Serializable {
    static final long serialVersionUID = 810L;

    private PaymentDto paymentDto;

    public PaymentEvent(PaymentDto paymentDto) {
        this.paymentDto = paymentDto;
    }
}
