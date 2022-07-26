package id.holigo.services.holigotransactionservice.schedulers;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentDto;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.common.model.TransactionDto;
import id.holigo.services.common.model.creditcard.PostpaidCreditcardTransactionDto;
import id.holigo.services.common.model.electricities.PostpaidElectricitiesTransactionDto;
import id.holigo.services.common.model.electricities.PrepaidElectricitiesTransactionDto;
import id.holigo.services.common.model.ewallet.PrepaidWalletTransactionDto;
import id.holigo.services.common.model.games.PrepaidGameTransactionDto;
import id.holigo.services.common.model.hotel.HotelTransactionDto;
import id.holigo.services.common.model.insurance.PostpaidInsuranceTransactionDto;
import id.holigo.services.common.model.multifinance.PostpaidMultifinanceTransactionDto;
import id.holigo.services.common.model.netv.PostpaidTvInternetTransactionDto;
import id.holigo.services.common.model.pdam.PostpaidPdamTransactionDto;
import id.holigo.services.common.model.pulsa.PrepaidPulsaTransactionDto;
import id.holigo.services.common.model.telephone.PostpaidTelephoneTransactionDto;
import id.holigo.services.holigotransactionservice.config.KafkaTopicConfig;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import id.holigo.services.holigotransactionservice.services.PaymentStatusTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.autoconfig.instrument.messaging.SleuthMessagingProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private final KafkaTemplate<String, PostpaidElectricitiesTransactionDto> postpaidElectricityKafkaTemplate;
    private final KafkaTemplate<String, PrepaidElectricitiesTransactionDto> prepaidElectricityKafkaTemplate;
    private final KafkaTemplate<String, PrepaidGameTransactionDto> gameKafkaTemplate;
    private final KafkaTemplate<String, PostpaidInsuranceTransactionDto> insuranceKafkaTemplate;
    private final KafkaTemplate<String, PostpaidMultifinanceTransactionDto> multiFinanceKafkaTemplate;
    private final KafkaTemplate<String, PostpaidTvInternetTransactionDto> tvInetKafkaTemplate;
    private final KafkaTemplate<String, PostpaidPdamTransactionDto> pdamKafkaTemplate;
    private final KafkaTemplate<String, PostpaidTelephoneTransactionDto> telephoneKafkaTemplate;
    private final KafkaTemplate<String, HotelTransactionDto> hotelKafkaTemplate;
    private final KafkaTemplate<String, PostpaidCreditcardTransactionDto> ccKafkaTemplate;
    private final KafkaTemplate<String, PaymentDto> paymentKafkaTemplate;

    @Scheduled(fixedRate = 10000)
    public void toExpiryOrder() {

        List<Transaction> transactions = transactionRepository
                .findAllByPaymentStatusInAndExpiredAtLessThanEqual(
                        List.of(PaymentStatusEnum.SELECTING_PAYMENT, PaymentStatusEnum.WAITING_PAYMENT),
                        Timestamp.valueOf(LocalDateTime.now()));
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
                case "DWAL":
                    ewalletKafkaTemplate.send(KafkaTopicConfig.UPDATE_EWALLET_TRANSACTION, PrepaidWalletTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED).build());
                    break;
                case "PAS":
                    postpaidElectricityKafkaTemplate.send(KafkaTopicConfig.UPDATE_POSTPAID_ELECTRICITY_TRANSACTION, PostpaidElectricitiesTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED)
                            .build());
                    break;
                case "PRA":
                    prepaidElectricityKafkaTemplate.send(KafkaTopicConfig.UPDATE_PREPAID_ELECTRICITY_TRANSACTION, PrepaidElectricitiesTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED)
                            .build());
                    break;
                case "GAME":
                    gameKafkaTemplate.send(KafkaTopicConfig.UPDATE_GAME_TRANSACTION, PrepaidGameTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED)
                            .build());
                    break;
                case "NETV":
                    tvInetKafkaTemplate.send(KafkaTopicConfig.UPDATE_TV_INTERNET_TRANSACTION, PostpaidTvInternetTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED)
                            .build());
                    break;
                case "PAM":
                    pdamKafkaTemplate.send(KafkaTopicConfig.UPDATE_PDAM_TRANSACTION, PostpaidPdamTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED)
                            .build());
                    break;
                case "INS":
                    insuranceKafkaTemplate.send(KafkaTopicConfig.UPDATE_INSURANCE_TRANSACTION, PostpaidInsuranceTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED)
                            .build());
                case "MFN":
                    multiFinanceKafkaTemplate.send(KafkaTopicConfig.UPDATE_MULTIFINANCE_TRANSACTION, PostpaidMultifinanceTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED)
                            .build());
                    break;
                case "TLP":
                    telephoneKafkaTemplate.send(KafkaTopicConfig.UPDATE_TELEPHONE_TRANSACTION, PostpaidTelephoneTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED)
                            .build());
                    break;
                case "CC":
                    log.info("CC kafka is running");
                    ccKafkaTemplate.send(KafkaTopicConfig.UPDATE_CC_TRANSACTION, PostpaidCreditcardTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED)
                            .build());
                    break;
                case "HTL":
                    hotelKafkaTemplate.send(KafkaTopicConfig.UPDATE_HOTEL_TRANSACTION, HotelTransactionDto.builder()
                            .id(Long.valueOf(transaction.getTransactionId()))
                            .paymentStatus(PaymentStatusEnum.PAYMENT_EXPIRED)
                            .orderStatus(OrderStatusEnum.ORDER_EXPIRED).build());
                    break;
                case "AIR":

                    break;

            }
        });
    }
}
