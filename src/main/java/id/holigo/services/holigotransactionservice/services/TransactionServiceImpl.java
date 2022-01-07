package id.holigo.services.holigotransactionservice.services;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.component.ProductDetail;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.web.mappers.TransactionMapper;
import id.holigo.services.holigotransactionservice.web.model.DetailProductForUser;
import id.holigo.services.holigotransactionservice.web.model.TransactionPaginateForUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

        @Autowired
        private final TransactionRepository transactionRepository;

        @Autowired
        private final TransactionMapper transactionMapper;

        @Autowired
        private final ProductDetail productDetail;

        @Override
        public TransactionPaginateForUser listTeaForUser(PageRequest pageRequest) {
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
                Transaction savedTransaction = transactionRepository
                                .save(transactionMapper.transactionDtoToTransaction(transactionDto));
                return transactionMapper.transactionToTransactionDto(savedTransaction);
        }

        @Override
        public DetailProductForUser detailProductTransaction(UUID id) throws JMSException {
                DetailProductForUser detailProduct = new DetailProductForUser();
                Optional<Transaction> retrieveTransaction = transactionRepository.findById(id);
                if (retrieveTransaction.isEmpty()) {
                        detailProduct.setMessage("Transaction tidak ditemukan!");
                        detailProduct.setStatus(false);
                        return detailProduct;
                }
                Transaction transaction = retrieveTransaction.get();

                detailProduct = productDetail.productSender(transaction.getTransactionType(),
                                transaction.getTransactionId());

                return detailProduct;
        }

}
