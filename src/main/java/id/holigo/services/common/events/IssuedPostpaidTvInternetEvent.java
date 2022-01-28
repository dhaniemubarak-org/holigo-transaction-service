package id.holigo.services.common.events;

import java.io.Serializable;

import id.holigo.services.common.model.netv.PostpaidTvInternetTransactionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class IssuedPostpaidTvInternetEvent implements Serializable {
    static final long serialVersionUID = -871231512L;

    private PostpaidTvInternetTransactionDto postpaidTvInternetTransactionDto;

    public IssuedPostpaidTvInternetEvent(PostpaidTvInternetTransactionDto postpaidTvInternetTransactionDto) {
        this.postpaidTvInternetTransactionDto = postpaidTvInternetTransactionDto;
    }
}
