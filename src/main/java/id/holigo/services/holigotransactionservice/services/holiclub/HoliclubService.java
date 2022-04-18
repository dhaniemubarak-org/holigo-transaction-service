package id.holigo.services.holigotransactionservice.services.holiclub;

import id.holigo.services.common.events.HoliclubEvent;
import id.holigo.services.common.model.IncrementUserClubDto;

public interface HoliclubService {

    void incrementUserClub(IncrementUserClubDto incrementUserClubDto);
}
