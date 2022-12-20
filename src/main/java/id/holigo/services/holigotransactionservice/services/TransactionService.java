package id.holigo.services.holigotransactionservice.services;

import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.web.model.TransactionDtoForUser;
import id.holigo.services.holigotransactionservice.web.model.TransactionFilterEnum;
import id.holigo.services.holigotransactionservice.web.model.TransactionPaginateForUser;
import org.springframework.data.domain.PageRequest;

import javax.jms.JMSException;
import java.sql.Date;
import java.util.UUID;

public interface TransactionService {

    TransactionPaginateForUser listTransactionForUser(Long userId, TransactionFilterEnum status, String transactionType, Date startDate, Date endDate, PageRequest pageRequest);

    TransactionDto createNewTransaction(TransactionDto transactionDto);

    TransactionDto getTransactionById(UUID id);

    TransactionDtoForUser getTransactionByIdForUser(UUID id) throws JMSException;

    void deleteTransaction(UUID transactionId, Long userId);

    void checkPaymentStatus(Transaction transaction);

    void updateDataSubsidyApSupplierTransaction(TransactionDto transactionDto);

}
