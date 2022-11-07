package id.holigo.services.holigotransactionservice.schedulers;


import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentStatusCheck {

    private TransactionRepository transactionRepository;

    private TransactionService transactionService;

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Scheduled(fixedRate = 5000)
    void checkWithDepositPayment() {
        List<Transaction> transactions = transactionRepository.findAllByPaymentServiceIdAndPaymentStatus("DEPOSIT", PaymentStatusEnum.WAITING_PAYMENT);
        transactions.forEach(transaction -> transactionService.checkPaymentStatus(transaction));
    }
}
