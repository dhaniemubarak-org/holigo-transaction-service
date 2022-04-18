package id.holigo.services.common.events;

import id.holigo.services.common.model.IncrementUserClubDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class HoliclubEvent implements Serializable {

    static final long serialVersionUID = 234L;

    private IncrementUserClubDto incrementUserClubDto;

    public HoliclubEvent(IncrementUserClubDto incrementUserClubDto) {
        this.incrementUserClubDto = incrementUserClubDto;
    }

}
