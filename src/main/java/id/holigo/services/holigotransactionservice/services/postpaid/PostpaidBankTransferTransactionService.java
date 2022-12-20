package id.holigo.services.holigotransactionservice.services.postpaid;

import id.holigo.services.common.model.banktransfer.PostpaidBankTransferTransactionDto;

public interface PostpaidBankTransferTransactionService {
    void issuedTransaction(PostpaidBankTransferTransactionDto postpaidBankTransferTransactionDto);
    Object getDetailTransaction(Long id);
}
