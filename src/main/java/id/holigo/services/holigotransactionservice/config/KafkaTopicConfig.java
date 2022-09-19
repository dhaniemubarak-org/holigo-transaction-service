package id.holigo.services.holigotransactionservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String UPDATE_PULSA_TRANSACTION = "update-pulsa-transaction";
    public static final String UPDATE_EWALLET_TRANSACTION = "update-ewallet-transaction";

    public static final String UPDATE_POSTPAID_ELECTRICITY_TRANSACTION = "update-postpaid-electricity-transaction";

    public static final String UPDATE_PREPAID_ELECTRICITY_TRANSACTION = "update-prepaid-electricity-transaction";

    public static final String UPDATE_CC_TRANSACTION = "update-cc-transaction";

    public static final String UPDATE_GAME_TRANSACTION = "update-game-transaction";

    public static final String UPDATE_INSURANCE_TRANSACTION = "update-insurance-transaction";

    public static final String UPDATE_TV_INTERNET_TRANSACTION = "update-tv-internet-transaction";

    public static final String UPDATE_MULTIFINANCE_TRANSACTION = "update-multifinance-transaction";

    public static final String UPDATE_PDAM_TRANSACTION = "update-pdam-transaction";

    public static final String UPDATE_TELEPHONE_TRANSACTION = "update-telephone-transaction";

    public static final String UPDATE_HOTEL_TRANSACTION = "update-hotel-transaction";

    public static final String UPDATE_AIRLINES_TRANSACTION = "update-airlines-transaction";
    public static final String UPDATE_PAYMENT = "update-payment";

    public static final String CANCEL_PAYMENT = "cancel-payment";

    public static final String UPDATE_DEPOSIT_TRANSACTION = "update-deposit-transaction";

    @Bean
    public NewTopic updatePulsaTransaction() {
        return TopicBuilder.name(UPDATE_PULSA_TRANSACTION).build();
    }

    @Bean
    public NewTopic updateEwalletTransaction() {
        return TopicBuilder.name(UPDATE_EWALLET_TRANSACTION).build();
    }

    @Bean
    public NewTopic updatePostpaidElectricityTransaction() {
        return TopicBuilder.name(UPDATE_POSTPAID_ELECTRICITY_TRANSACTION).build();
    }

    @Bean
    public NewTopic updatePrepaidElectricityTransaction() {
        return TopicBuilder.name(UPDATE_PREPAID_ELECTRICITY_TRANSACTION).build();
    }

    @Bean
    public NewTopic updateCCTransaction() {
        return TopicBuilder.name(UPDATE_CC_TRANSACTION).build();
    }

    @Bean
    public NewTopic updateGameTransaction() {
        return TopicBuilder.name(UPDATE_GAME_TRANSACTION).build();
    }

    @Bean
    public NewTopic updateInsuranceTransaction() {
        return TopicBuilder.name(UPDATE_INSURANCE_TRANSACTION).build();
    }

    @Bean
    public NewTopic updateInternetTransaction() {
        return TopicBuilder.name(UPDATE_TV_INTERNET_TRANSACTION).build();
    }

    @Bean
    public NewTopic updatePdamTransaction() {
        return TopicBuilder.name(UPDATE_PDAM_TRANSACTION).build();
    }

    @Bean
    public NewTopic updateTelephoneTransaction() {
        return TopicBuilder.name(UPDATE_TELEPHONE_TRANSACTION).build();
    }

    @Bean
    public NewTopic updateMultifinanceTransaction() {
        return TopicBuilder.name(UPDATE_MULTIFINANCE_TRANSACTION).build();
    }

    public NewTopic updateHotelTransaction() {
        return TopicBuilder.name(UPDATE_HOTEL_TRANSACTION).build();
    }

    @Bean
    public NewTopic updatePayment() {
        return TopicBuilder.name(UPDATE_PAYMENT).build();
    }

    @Bean
    public NewTopic cancelPayment() {
        return TopicBuilder.name(CANCEL_PAYMENT).build();
    }

    @Bean
    public NewTopic updateDepositTransaction() {
        return TopicBuilder.name(UPDATE_DEPOSIT_TRANSACTION).build();
    }


}
