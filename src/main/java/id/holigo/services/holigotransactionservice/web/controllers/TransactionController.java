package id.holigo.services.holigotransactionservice.web.controllers;

import id.holigo.services.holigotransactionservice.services.OrderStatusTransactionService;
import id.holigo.services.holigotransactionservice.services.PaymentStatusTransactionService;
import id.holigo.services.holigotransactionservice.services.TransactionService;
import id.holigo.services.holigotransactionservice.web.model.TransactionDtoForUser;
import id.holigo.services.holigotransactionservice.web.model.TransactionFilterEnum;
import id.holigo.services.holigotransactionservice.web.model.TransactionPaginateForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import java.sql.Date;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private final OrderStatusTransactionService orderStatusTransactionService;

    private final PaymentStatusTransactionService paymentStatusTransactionService;

    @GetMapping(path = {"/api/v1/transactions"})
    public ResponseEntity<TransactionPaginateForUser> getAllTransactions(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "status", required = false) TransactionFilterEnum status,
            @RequestParam(value = "transactionType", required = false) String transactionType,
            @RequestParam(value = "startDate", required = false) Date startDate,
            @RequestParam(value = "endDate", required = false) Date endDate,
            @RequestHeader(value = "user-id") Long userId) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        TransactionPaginateForUser transactionList = transactionService
                .listTransactionForUser(userId, status, transactionType, startDate, endDate, PageRequest.of(pageNumber, pageSize,
                        Sort.by("createdAt").descending()));

        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }

    @GetMapping(path = {"/api/v1/transactions/{id}"})
    @Transactional
    public ResponseEntity<TransactionDtoForUser> getDetailTransaction(@PathVariable("id") UUID id) throws JMSException {
        TransactionDtoForUser transactionDtoForUser = transactionService.getTransactionByIdForUser(id);
        if (transactionDtoForUser != null) {
            return new ResponseEntity<>(transactionDtoForUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/api/v1/transactions/{id}")
    public ResponseEntity<TransactionDtoForUser> cancelTransaction(@PathVariable("id") UUID id, @RequestHeader("user-id") Long userId) throws JMSException {
        orderStatusTransactionService.cancelTransaction(id);
        paymentStatusTransactionService.paymentHasCanceled(id);
        TransactionDtoForUser transactionDtoForUser = transactionService.getTransactionByIdForUser(id);
        return new ResponseEntity<>(transactionDtoForUser, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/v1/transactions/{id}")
    public ResponseEntity<HttpStatus> deleteTransaction(@PathVariable("id") UUID id, @RequestHeader("user-id") Long userId) throws JMSException {
        transactionService.deleteTransaction(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
