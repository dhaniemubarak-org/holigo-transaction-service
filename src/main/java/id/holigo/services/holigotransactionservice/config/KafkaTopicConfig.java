package id.holigo.services.holigotransactionservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String UPDATE_PULSA_TRANSACTION = "update-pulsa-transaction";

    public static final String UPDATE_EWALLET_TRANSACTION = "update-ewallet-transaction";

    public static final String UPDATE_PAYMENT = "update-payment";

    @Bean
    public NewTopic updatePulsaTransaction() {
        return TopicBuilder.name(UPDATE_PULSA_TRANSACTION).build();
    }

    @Bean
    public NewTopic updateEwalletTransaction() {
        return TopicBuilder.name(UPDATE_EWALLET_TRANSACTION).build();
    }

    @Bean
    public NewTopic updatePayment() {
        return TopicBuilder.name(UPDATE_PAYMENT).build();
    }


}
