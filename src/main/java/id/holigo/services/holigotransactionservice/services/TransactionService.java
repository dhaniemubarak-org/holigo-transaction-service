package id.holigo.services.holigotransactionservice.services;

import java.util.UUID;

import javax.jms.JMSException;

import org.springframework.data.domain.PageRequest;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.web.model.TransactionDtoForUser;
import id.holigo.services.holigotransactionservice.web.model.TransactionPaginateForUser;

public interface TransactionService {

    TransactionPaginateForUser listTransactionForUser(PageRequest pageRequest, Long userId);

    TransactionDto createNewTransaction(TransactionDto transactionDto);

    TransactionDto getTransactionById(UUID id);

    TransactionDtoForUser getTransactionByIdForUser(UUID id) throws JMSException;

}
