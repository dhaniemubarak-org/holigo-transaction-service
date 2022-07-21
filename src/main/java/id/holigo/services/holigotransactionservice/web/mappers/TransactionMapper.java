package id.holigo.services.holigotransactionservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.web.model.TransactionDtoForUser;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@DecoratedWith(TransactionMapperDecorator.class)
@Mapper
public interface TransactionMapper {

    @Mapping(target = "voucherCode", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "serviceId", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "prcAmount", ignore = true)
    @Mapping(target = "prAmount", ignore = true)
    @Mapping(target = "pointAmount", ignore = true)
    @Mapping(target = "paymentServiceId", ignore = true)
    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "note", ignore = true)
    @Mapping(target = "mpAmount", ignore = true)
    @Mapping(target = "lossAmount", ignore = true)
    @Mapping(target = "ipcAmount", ignore = true)
    @Mapping(target = "ipAmount", ignore = true)
    @Mapping(target = "indexUser", ignore = true)
    @Mapping(target = "hvAmount", ignore = true)
    @Mapping(target = "hpcAmount", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "cpAmount", ignore = true)
    @Mapping(target = "adminAmount", ignore = true)
    Transaction transactionDtoForUserToTransaction(TransactionDtoForUser transactionDtoForUser);

    @Mapping(target = "serverTime", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "detail", ignore = true)
    TransactionDtoForUser transactionToTransactionDtoForUser(Transaction transaction);

    Transaction transactionDtoToTransaction(TransactionDto transactionDto);

    TransactionDto transactionToTransactionDto(Transaction transaction);

}
