package id.holigo.services.holigotransactionservice.services.postpaid;

import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.common.model.gas.PostpaidGasTransactionDto;

import javax.jms.JMSException;

public interface PostpaidGasTransactionService {
    void issuedTransaction(PostpaidGasTransactionDto postpaidGasTransactionDto);
    DetailProductTransaction sendDetailProduct(Long productId) throws JMSException;
}
