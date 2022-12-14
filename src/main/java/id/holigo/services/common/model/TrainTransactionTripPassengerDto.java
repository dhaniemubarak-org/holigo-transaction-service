package id.holigo.services.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainTransactionTripPassengerDto implements Serializable {

    private PassengerDto passenger;

    private String seatNumber;

}
