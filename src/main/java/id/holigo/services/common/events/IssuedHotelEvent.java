package id.holigo.services.common.events;

import id.holigo.services.common.model.hotel.HotelTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class IssuedHotelEvent implements Serializable {

    static final long serialVersionUID = 6190285123L;

    private HotelTransactionDto hotelTransactionDto;

    public IssuedHotelEvent(HotelTransactionDto hotelTransactionDto){
        this.hotelTransactionDto = hotelTransactionDto;
    }
}
