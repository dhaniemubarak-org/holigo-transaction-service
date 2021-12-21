package id.holigo.services.holigotransactionservice.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.web.mappers.TransactionMapper;
import id.holigo.services.holigotransactionservice.web.model.TransactionPaginateForUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionPaginateForUser listTeaForUser(PageRequest pageRequest) {
        TransactionPaginateForUser transactionPaginateForUser;
        Page<Transaction> transactionPage;

        transactionPage = transactionRepository.findAll(pageRequest);

        transactionPaginateForUser = new TransactionPaginateForUser(
                transactionPage.getContent().stream().map(transactionMapper::transactionToTransactionDtoForUser)
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

}
