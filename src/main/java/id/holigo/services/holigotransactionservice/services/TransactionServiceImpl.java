package id.holigo.services.holigotransactionservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentDtoForUser;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.component.ProductRoute;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.events.PaymentStatusEvent;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.repositories.specification.GenericAndSpecification;
import id.holigo.services.holigotransactionservice.repositories.specification.SearchCriteria;
import id.holigo.services.holigotransactionservice.repositories.specification.SearchOperation;
import id.holigo.services.holigotransactionservice.repositories.specification.TransactionSpecification;
import id.holigo.services.holigotransactionservice.services.payment.PaymentService;
import id.holigo.services.holigotransactionservice.web.mappers.TransactionMapper;
import id.holigo.services.holigotransactionservice.web.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final PaymentStatusTransactionService paymentStatusTransactionService;

    private final TransactionSpecification transactionSpecification;

    private final PaymentService paymentService;

    private ProductRoute productRoute;

    @Autowired
    void setProductRoute(ProductRoute productRoute) {
        this.productRoute = productRoute;
    }

    private final OrderStatusTransactionService orderStatusTransactionService;

    @Override
    public TransactionPaginateForUser listTransactionForUser(Long userId, TransactionFilterEnum status, String transactionType, Date startDate, Date endDate, PageRequest pageRequest) {
        TransactionPaginateForUser transactionPaginateForUser;
        Page<Transaction> transactionPage;

        List<OrderStatusEnum> orderStatus = new ArrayList<>();
        List<PaymentStatusEnum> paymentStatus = new ArrayList<>();
        List<String> serviceCodes = new ArrayList<>();
        Specification<Transaction> getByOrder = transactionSpecification.getOrderStatus(orderStatus);
        Specification<Transaction> getByPayment = transactionSpecification.getPaymentStatus(paymentStatus);
        Specification<Transaction> getStartDate = transactionSpecification.getByStartDate(startDate);
        Specification<Transaction> getEndDate = transactionSpecification.getByEndDate(endDate);
        Specification<Transaction> getDeleted = transactionSpecification.getDeletedAtNull();
        Specification<Transaction> getTransactionType = transactionSpecification.getTransactionType(serviceCodes);

        GenericAndSpecification<Transaction> genericAndSpecification = new GenericAndSpecification<>();
        genericAndSpecification.add(new SearchCriteria("userId", userId, SearchOperation.EQUAL));

        if (transactionType != null && !transactionType.equals("")) {
            switch (transactionType) {
                case "PRE" -> {
                    PrepaidEnum[] prepaidEnums = PrepaidEnum.values();
                    for (PrepaidEnum prepaidEnum : prepaidEnums
                    ) {
                        serviceCodes.add(prepaidEnum.name());
                    }
                }
                case "POST" -> {
                    PostpaidEnum[] postpaidEnums = PostpaidEnum.values();
                    for (PostpaidEnum postpaidEnum : postpaidEnums
                    ) {
                        serviceCodes.add(postpaidEnum.name());
                    }
                }
                default ->
                        genericAndSpecification.add(new SearchCriteria("transactionType", transactionType, SearchOperation.EQUAL));

            }
        }

        if (status != null) {
            switch (status) {
                case SELECTING_PAYMENT -> {
                    paymentStatus.add(PaymentStatusEnum.SELECTING_PAYMENT);
                    orderStatus.add(OrderStatusEnum.PROCESS_BOOK);
                    orderStatus.add(OrderStatusEnum.BOOKED);
                }
                case WAITING_PAYMENT -> {
                    paymentStatus.add(PaymentStatusEnum.WAITING_PAYMENT);
                    orderStatus.add(OrderStatusEnum.PROCESS_BOOK);
                    orderStatus.add(OrderStatusEnum.BOOKED);
                }
                case PAID -> {
                    paymentStatus.add(PaymentStatusEnum.PAID);
                    orderStatus.add(OrderStatusEnum.BOOKED);
                }
                case PROCESS_ISSUED -> {
                    paymentStatus.add(PaymentStatusEnum.PAID);
                    orderStatus.add(OrderStatusEnum.PROCESS_ISSUED);
                    orderStatus.add(OrderStatusEnum.WAITING_ISSUED);
                    orderStatus.add(OrderStatusEnum.RETRYING_ISSUED);
                }
                case ISSUED -> {
                    paymentStatus.add(PaymentStatusEnum.PAID);
                    orderStatus.add(OrderStatusEnum.ISSUED);
                }
                case ISSUED_FAILED -> {
                    paymentStatus.add(PaymentStatusEnum.PAID);
                    paymentStatus.add(PaymentStatusEnum.PROCESS_REFUND);
                    paymentStatus.add(PaymentStatusEnum.WAITING_REFUND);
                    paymentStatus.add(PaymentStatusEnum.REFUNDED);
                    orderStatus.add(OrderStatusEnum.ISSUED_FAILED);
                }
                case ORDER_EXPIRED -> {
                    paymentStatus.add(PaymentStatusEnum.PAYMENT_EXPIRED);
                    orderStatus.add(OrderStatusEnum.ORDER_EXPIRED);
                }
                case ORDER_CANCELED -> {
                    paymentStatus.add(PaymentStatusEnum.PAYMENT_CANCELED);
                    orderStatus.add(OrderStatusEnum.ORDER_CANCELED);
                }
            }

        }

        transactionPage = transactionRepository.findAll(Specification.where(genericAndSpecification).and(getByOrder.and(getByPayment)).and(getStartDate.and(getEndDate)).and(getDeleted).and(getTransactionType), pageRequest);


        transactionPaginateForUser = new TransactionPaginateForUser(
                transactionPage.getContent().stream()
                        .map(transactionMapper::transactionToTransactionDtoForUser)
                        .collect(Collectors.toList()),
                PageRequest.of(transactionPage.getPageable().getPageNumber(),
                        transactionPage.getPageable().getPageSize()),
                transactionPage.getTotalElements());

        return transactionPaginateForUser;
    }

    @Override
    public TransactionDto createNewTransaction(TransactionDto transactionDto) {
        if (transactionDto.getExpiredAt() == null) {
            transactionDto.setExpiredAt(Timestamp.valueOf(LocalDateTime.now().plusHours(2)));
        }
        transactionDto.setInvoiceNumber(generateInvoiceNumber(transactionDto));
        transactionDto.setOrderStatus(OrderStatusEnum.PROCESS_BOOK);
        transactionDto.setPaymentStatus(PaymentStatusEnum.SELECTING_PAYMENT);
        Transaction savedTransaction = transactionRepository
                .save(transactionMapper.transactionDtoToTransaction(transactionDto));
        orderStatusTransactionService.bookingSuccess(savedTransaction.getId());
        return transactionMapper.transactionToTransactionDto(savedTransaction);
    }

    @Override
    public TransactionDto getTransactionById(UUID id) {
        Optional<Transaction> fetchTransaction = transactionRepository.findById(id);
        return fetchTransaction.map(transactionMapper::transactionToTransactionDto).orElse(null);
    }

    @Override
    public TransactionDtoForUser getTransactionByIdForUser(UUID id) throws JMSException {
        Optional<Transaction> fetchTransaction = transactionRepository.findById(id);
        if (fetchTransaction.isPresent()) {
            TransactionDtoForUser transactionDtoForUser = transactionMapper
                    .transactionToTransactionDtoForUser(fetchTransaction.get());
            Object detailProduct = productRoute.getDetailProduct(
                    transactionDtoForUser.getTransactionType(),
                    Long.valueOf(transactionDtoForUser.getTransactionId()), LocaleContextHolder.getLocale().toString());

            transactionDtoForUser.setDetail(detailProduct);
            return transactionDtoForUser;
        }
        return null;
    }

    @Override
    public void deleteTransaction(UUID transactionId, Long userId) {
        Optional<Transaction> fetchTransaction = transactionRepository.findById(transactionId);
        if (fetchTransaction.isPresent()) {
            Transaction transaction = fetchTransaction.get();
            if (transaction.getUserId().equals(userId)) {
                if (!transaction.getOrderStatus().equals(OrderStatusEnum.ISSUED)) {
                    paymentStatusTransactionService.paymentHasCanceled(transaction.getId());
                    orderStatusTransactionService.cancelTransaction(transaction.getId());
                }
                transaction.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
                transactionRepository.save(transaction);
                if (transaction.getPaymentStatus().equals(PaymentStatusEnum.WAITING_PAYMENT)) {
                    paymentStatusTransactionService.paymentHasCanceled(transaction.getId());
                }
            }
        }
    }

    @Transactional
    @Override
    public void checkPaymentStatus(Transaction transaction) {
        PaymentDtoForUser payment = paymentService.getPayment(transaction.getPaymentId());
        if (payment.getStatus().equals(PaymentStatusEnum.PAID)) {
            StateMachine<PaymentStatusEnum, PaymentStatusEvent> paidTransaction = paymentStatusTransactionService.transactionHasBeenPaid(transaction.getId());
            if (paidTransaction.getState().getId().equals(PaymentStatusEnum.PAID)) {
                orderStatusTransactionService.processIssued(transaction.getId());
            }
        }
    }

    private String generateInvoiceNumber(TransactionDto transactionDto) {

        return transactionDto.getServiceId().toString() + "/"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "/" + transactionDto.getTransactionId();
    }

    @Override
    public void updateDataSubsidyApSupplierTransaction(TransactionDto transactionDto) {
        Optional<Transaction> fetchTransaction = transactionRepository.findById(transactionDto.getId());
        if (fetchTransaction.isPresent()) {
            log.info("Updated Data -> {}", transactionDto);
            Transaction transaction = fetchTransaction.get();
            transaction.setSupplierTransactionId(transactionDto.getSupplierTransactionId());
            transaction.setIndexProduct(transactionDto.getIndexProduct());
            transaction.setApAmount(transactionDto.getApAmount());
            transaction.setSubsidyAmount(transactionDto.getSubsidyAmount());
            transaction.setNote(transactionDto.getNote());
            transactionRepository.save(transaction);
        }
    }

}
