package id.holigo.services.holigotransactionservice.services;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.component.ProductRoute;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.web.mappers.TransactionMapper;
import id.holigo.services.holigotransactionservice.web.model.TransactionDtoForUser;
import id.holigo.services.holigotransactionservice.web.model.TransactionPaginateForUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

        public static final String TRANSACTION_HEADER = "payment_id";

        @Autowired
        private final TransactionRepository transactionRepository;

        @Autowired
        private final TransactionMapper transactionMapper;

        @Autowired
        private final ProductRoute productRoute;

        private final OrderStatusTransactionService orderStatusTransactionService;

        @Override
        public TransactionPaginateForUser listTransactionForUser(PageRequest pageRequest) {
                TransactionPaginateForUser transactionPaginateForUser;
                Page<Transaction> transactionPage;

                transactionPage = transactionRepository.findAll(pageRequest);

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
                transactionDto.setOrderStatus(OrderStatusEnum.PROCESS_BOOK);
                transactionDto.setPaymentStatus(PaymentStatusEnum.WAITING_PAYMENT);
                Transaction savedTransaction = transactionRepository
                                .save(transactionMapper.transactionDtoToTransaction(transactionDto));
                orderStatusTransactionService.bookingSuccess(savedTransaction.getId());
                return transactionMapper.transactionToTransactionDto(savedTransaction);
        }

        @Override
        public TransactionDto getTransactionById(UUID id) {
                Optional<Transaction> fetchTransaction = transactionRepository.findById(id);
                if (fetchTransaction.isPresent()) {
                        TransactionDto transactionDto = transactionMapper
                                        .transactionToTransactionDto(fetchTransaction.get());
                        return transactionDto;
                }
                return null;
        }

        @Override
        @Transactional
        public TransactionDtoForUser getTransactionByIdForUser(UUID id) throws JMSException {
                Optional<Transaction> fetchTransaction = transactionRepository.findById(id);
                if (fetchTransaction.isPresent()) {
                        TransactionDtoForUser transactionDtoForUser = transactionMapper
                                        .transactionToTransactionDtoForUser(fetchTransaction.get());

                        Object detailProduct = productRoute.getDetailProduct(
                                        transactionDtoForUser.getTransactionType(),
                                        Long.valueOf(transactionDtoForUser.getTransactionId()));

                        transactionDtoForUser.setDetail(detailProduct);
                        return transactionDtoForUser;
                }
                return null;
        }

}
