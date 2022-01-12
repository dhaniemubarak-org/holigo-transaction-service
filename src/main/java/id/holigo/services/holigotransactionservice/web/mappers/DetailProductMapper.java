package id.holigo.services.holigotransactionservice.web.mappers;

import org.mapstruct.Mapper;

import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.holigotransactionservice.web.model.DetailProductDtoForUser;

@Mapper
public interface DetailProductMapper {

    DetailProductDtoForUser detailProductTransactionToDetailProductForUser(DetailProductTransaction productTransaction);
}
