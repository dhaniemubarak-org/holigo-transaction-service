package id.holigo.services.holigotransactionservice.services;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.sender.ProductPulsa;
import id.holigo.services.holigotransactionservice.web.mappers.TransactionMapper;
import id.holigo.services.holigotransactionservice.web.model.DetailProductDtoForUser;
import id.holigo.services.holigotransactionservice.web.model.ProductTransaction;
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
        private final ProductPulsa productPulsa;

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
                Transaction savedTransaction = transactionRepository
                                .save(transactionMapper.transactionDtoToTransaction(transactionDto));
                return transactionMapper.transactionToTransactionDto(savedTransaction);
        }

        @Override
        public DetailProductDtoForUser detailProductTransaction(UUID id) throws JMSException {
                DetailProductDtoForUser detailProduct = new DetailProductDtoForUser();
                Optional<Transaction> retrieveTransaction = transactionRepository.findById(id);
                if (retrieveTransaction.isEmpty()) {
                        detailProduct.setMessage("Transaction tidak ditemukan!");
                        detailProduct.setStatus(false);
                        return detailProduct;
                }
                Transaction transaction = retrieveTransaction.get();

                DetailProductTransaction product = productPulsa.sendDetailProduct(transaction.getProductId());
                ProductTransaction productTrx = ProductTransaction.builder().imageUrl(product.getImageUrl())
                                .nominalSelected(product.getNominalSelected()).description(product.getDescription())
                                .price(product.getPrice()).build();

                DetailProductDtoForUser productForUser = DetailProductDtoForUser.builder().name(product.getName())
                                .type(transaction.getTransactionType()).product(productTrx).build();

                return productForUser;
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

}
