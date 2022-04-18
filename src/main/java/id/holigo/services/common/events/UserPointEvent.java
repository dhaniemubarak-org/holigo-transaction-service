package id.holigo.services.common.events;


import id.holigo.services.common.model.UpdateUserPointDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserPointEvent implements Serializable {
    static final long serialVersionUID = 3456L;

    private UpdateUserPointDto updateUserPointDto;

    public UserPointEvent(UpdateUserPointDto updateUserPointDto) {
        this.updateUserPointDto = updateUserPointDto;
    }

}
