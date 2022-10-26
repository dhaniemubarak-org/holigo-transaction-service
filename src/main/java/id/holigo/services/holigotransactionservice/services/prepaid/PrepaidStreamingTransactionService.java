package id.holigo.services.holigotransactionservice.services.prepaid;

import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.common.model.streaming.PrepaidStreamingTransactionDto;

import javax.jms.JMSException;

public interface PrepaidStreamingTransactionService {
    void issuedTransaction(PrepaidStreamingTransactionDto prepaidStreamingTransactionDto);
    DetailProductTransaction sendDetailProduct(Long productId) throws JMSException;
}
