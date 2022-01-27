package id.holigo.services.holigotransactionservice.web.controllers;

import java.util.UUID;

import javax.jms.JMSException;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.holigo.services.holigotransactionservice.services.TransactionService;
// import id.holigo.services.holigotransactionservice.web.model.DetailProductDtoForUser;
import id.holigo.services.holigotransactionservice.web.model.TransactionDtoForUser;
import id.holigo.services.holigotransactionservice.web.model.TransactionPaginateForUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    @GetMapping(path = { "/api/v1/transactions" })
    public ResponseEntity<TransactionPaginateForUser> getAllTransactions(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        TransactionPaginateForUser transactionList = transactionService
                .listTransactionForUser(PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }

    @GetMapping(path = { "/api/v1/transactions/{id}"})
    @Transactional
    public ResponseEntity<TransactionDtoForUser> getDetailTransaction(@PathVariable("id") UUID id) throws JMSException{
        TransactionDtoForUser transactionDtoForUser = transactionService.getTransactionByIdForUser(id);
        
        if(transactionDtoForUser != null){
            return new ResponseEntity<>(transactionDtoForUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(transactionDtoForUser, HttpStatus.NOT_FOUND); 
    }

    // @GetMapping(path = { "/api/v1/transactions/{id}/product" })
    // public ResponseEntity<DetailProductDtoForUser> getDetailProductTransaction(@PathVariable("id") UUID id)
    //         throws JMSException {
    //     DetailProductDtoForUser detailProduct = transactionService.detailProductTransaction(id);
    //     return new ResponseEntity<>(detailProduct, HttpStatus.OK);
    // }
}
