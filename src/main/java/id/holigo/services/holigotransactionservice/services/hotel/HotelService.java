package id.holigo.services.holigotransactionservice.services.hotel;

import id.holigo.services.common.model.hotel.HotelTransactionDto;

public interface HotelService {

    void issuedTransaction(HotelTransactionDto hotelTransactionDto);
}
