package id.holigo.services.holigotransactionservice.services;

import java.util.UUID;

import javax.jms.JMSException;

import org.springframework.data.domain.PageRequest;

import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.web.model.DetailProductForUser;
import id.holigo.services.holigotransactionservice.web.model.TransactionPaginateForUser;

public interface TransactionService {

    TransactionPaginateForUser listTeaForUser(PageRequest pageRequest);

    TransactionDto createNewTransaction(TransactionDto transactionDto);

    DetailProductForUser detailProductTransaction(UUID id) throws JMSException;
}
