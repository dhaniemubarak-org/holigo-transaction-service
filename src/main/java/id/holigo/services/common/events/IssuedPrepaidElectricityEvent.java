package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.electricities.PrepaidElectricitiesTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class IssuedPrepaidElectricityEvent implements Serializable {

    static final long serialVersionUID = -95181210L;

    private PrepaidElectricitiesTransactionDto prepaidElectricitiesTransactionDto;

    public IssuedPrepaidElectricityEvent(PrepaidElectricitiesTransactionDto prepaidElectricitiesTransactionDto) {
        this.prepaidElectricitiesTransactionDto = prepaidElectricitiesTransactionDto;
    }
}
