package id.holigo.services.holigotransactionservice.web.mappers;

import id.holigo.services.common.model.UserDto;
import id.holigo.services.holigotransactionservice.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import id.holigo.services.common.model.PaymentDtoForUser;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.services.payment.PaymentService;
import id.holigo.services.holigotransactionservice.web.model.TransactionDtoForUser;

public abstract class TransactionMapperDecorator implements TransactionMapper {

    private TransactionMapper transactionMapper;

    private PaymentService paymentService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    public void setTransactionMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Transaction transactionDtoForUserToTransaction(TransactionDtoForUser transactionDtoForUser) {
        return transactionMapper.transactionDtoForUserToTransaction(transactionDtoForUser);
    }

    public TransactionDtoForUser transactionToTransactionDtoForUser(Transaction transaction) {
        TransactionDtoForUser transactionDtoForUser = transactionMapper.transactionToTransactionDtoForUser(transaction);

        if (transactionDtoForUser.getPaymentId() != null) {
            PaymentDtoForUser paymentDtoForUser = paymentService.getPayment(transactionDtoForUser.getPaymentId());
            paymentDtoForUser.setDetailRoute("/api/v1/payments/" + paymentDtoForUser.getId().toString() + "/"
                    + paymentDtoForUser.getDetailType() + "/" + paymentDtoForUser.getDetailId());
            transactionDtoForUser.setPayment(paymentDtoForUser);
        }
        return transactionDtoForUser;
    }

    public Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.transactionDtoToTransaction(transactionDto);
        UserDto userDto = userService.getUser(transactionDto.getUserId());
        if (userDto != null) {
            transaction.setUserParentId(userDto.getParent().getId());
            transaction.setOfficialId(userDto.getOfficialId());
        }
        return transactionMapper.transactionDtoToTransaction(transactionDto);
    }

    public TransactionDto transactionToTransactionDto(Transaction transaction) {
        return transactionMapper.transactionToTransactionDto(transaction);
    }
}
