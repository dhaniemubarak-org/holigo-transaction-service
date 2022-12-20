package id.holigo.services.holigotransactionservice.services.postpaid;

import id.holigo.services.common.events.IssuedPostpaidBankTransferEvent;
import id.holigo.services.common.events.IssuedPostpaidCreditcardEvent;
import id.holigo.services.common.model.banktransfer.PostpaidBankTransferTransactionDto;
import id.holigo.services.common.model.creditcard.PostpaidCreditcardTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostpaidBankTransferTransactionServiceImpl implements PostpaidBankTransferTransactionService {

    private final JmsTemplate jmsTemplate;
    private final PostpaidBankTransferServiceFeignClient postpaidBankTransferServiceFeignClient;
    @Override
    public void issuedTransaction(PostpaidBankTransferTransactionDto postpaidBankTransferTransactionDto) {
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_POSTPAID_BANKTRANSFER_BY_ID,
                new IssuedPostpaidBankTransferEvent(postpaidBankTransferTransactionDto));
    }
    @Override
    public Object getDetailTransaction(Long id){
        return postpaidBankTransferServiceFeignClient.getDetailTransaction(id).getBody();
    }

}
