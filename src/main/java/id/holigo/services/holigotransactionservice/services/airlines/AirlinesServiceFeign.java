package id.holigo.services.holigotransactionservice.services.airlines;

import id.holigo.services.common.model.AirlinesTransactionDtoForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AirlinesServiceFeign implements AirlinesService {

    private final AirlinesServiceFeignClient airlinesServiceFeignClient;

    @Override
    public AirlinesTransactionDtoForUser getTransaction(Long id) {
        ResponseEntity<AirlinesTransactionDtoForUser> responseEntity = airlinesServiceFeignClient.getTransaction(id);
        return responseEntity.getBody();
    }

    @Override
    public void cancelTransaction(Long id) {
        airlinesServiceFeignClient.cancelTransaction(id);
    }

}
