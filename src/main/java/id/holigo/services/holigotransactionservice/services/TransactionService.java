package id.holigo.services.holigotransactionservice.services;

import org.springframework.data.domain.PageRequest;

import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.web.model.TransactionPaginateForUser;

public interface TransactionService {

    TransactionPaginateForUser listTeaForUser(PageRequest pageRequest);

    TransactionDto createNewTransaction(TransactionDto transactionDto);
}
