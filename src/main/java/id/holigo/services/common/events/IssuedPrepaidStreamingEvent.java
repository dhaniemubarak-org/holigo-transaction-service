package id.holigo.services.common.events;

import id.holigo.services.common.model.streaming.PrepaidStreamingTransactionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class IssuedPrepaidStreamingEvent implements Serializable {
    static final long serialVersionUID = -9951235123L;
    private PrepaidStreamingTransactionDto prepaidStreamingTransactionDto;

    public IssuedPrepaidStreamingEvent(PrepaidStreamingTransactionDto prepaidStreamingTransactionDto){
        this.prepaidStreamingTransactionDto = prepaidStreamingTransactionDto;
    }
}
