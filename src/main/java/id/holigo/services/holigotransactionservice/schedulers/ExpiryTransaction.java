package id.holigo.services.holigotransactionservice.schedulers;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentDto;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.common.model.ewallet.PrepaidWalletTransactionDto;
import id.holigo.services.common.model.pulsa.PrepaidPulsaTransactionDto;
import id.holigo.services.holigotransactionservice.config.KafkaTopicConfig;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.services.PaymentStatusTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class ExpiryTransaction {

    private final TransactionRepository transactionRepository;

    private final PaymentStatusTransactionService paymentStatusTransactionService;

    private final KafkaTemplate<String, PrepaidWalletTransactionDto> ewalletKafkaTemplate;
    private final KafkaTemplate<String, PrepaidPulsaTransactionDto> pulsaKafkaTemplate;

    private final KafkaTemplate<String, PaymentDto> paymentKafkaTemplate;

    @Scheduled(fixedRate = 10000)
    public void toExpiryOrder() {

        List<Transaction> transactions = transactionRepository
                .findAllByPaymentStatusIn(List.of(PaymentStatusEnum.SELECTING_PAYMENT, PaymentStatusEnum.WAITING_PAYMENT));
        transactions.forEach(transaction -> {
            paymentStatusTransactionService.paymentHasExpired(transaction.getId());
            if (transaction.getPaymentServiceId() != null) {
                paymentKafkaTemplate.send(KafkaTopicConfig.UPDATE_PAYMENT, PaymentDto.builder()
                        .status(PaymentStatusEnum.PAYMENT_EXPIRED)
                        .transactionId(transaction.getId()).build());
            }
            switch (transaction.getTransactionType()) {
                case "PUL", "PR", "PD":
                    pulsaKafkaTemplate.send(KafkaTopicConfig.UPDATE_PULSA_TRANSACTION, PrepaidPulsaTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED).build());
                    break;
                case "EWAL":
                    ewalletKafkaTemplate.send(KafkaTopicConfig.UPDATE_EWALLET_TRANSACTION, PrepaidWalletTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED).build());
                    break;
                case "PAS":
                    break;
                case "PRA":
                    break;
                case "GAME":
                    break;
                case "DWAL":
                    break;
                case "NETV":
                    break;
                case "PAM":
                    break;
                case "INS":
                case "HTL":
                    break;
                case "MFN":
                    break;
                case "TLP":
                    break;
            }
        });
    }
}
