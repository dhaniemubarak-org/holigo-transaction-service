package id.holigo.services.holigotransactionservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.web.model.TransactionDtoForUser;

@DecoratedWith(TransactionMapperDecorator.class)
@Mapper
public interface TransactionMapper {

    Transaction transactionDtoForUserToTransaction(TransactionDtoForUser transactionDtoForUser);

    TransactionDtoForUser transactionToTransactionDtoForUser(Transaction transaction);

    Transaction transactionDtoToTransaction(TransactionDto transactionDto);

    TransactionDto transactionToTransactionDto(Transaction transaction);

}
