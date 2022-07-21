package id.holigo.services.holigotransactionservice.config;

import id.holigo.services.common.model.PaymentDto;
import id.holigo.services.common.model.ewallet.PrepaidWalletTransactionDto;
import id.holigo.services.common.model.pulsa.PrepaidPulsaTransactionDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(("${spring.kafka.bootstrap-servers}"))
    private String bootstrapServers;

    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, PrepaidPulsaTransactionDto> pulsaTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, PrepaidWalletTransactionDto> ewalletTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, PaymentDto> paymentProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, PrepaidPulsaTransactionDto> pulsaKafkaTemplate(ProducerFactory<String, PrepaidPulsaTransactionDto> pulsaTransactionProducer) {
        return new KafkaTemplate<>(pulsaTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PrepaidWalletTransactionDto> ewalletKafkaTemplate(ProducerFactory<String, PrepaidWalletTransactionDto> ewalletTransactionProducer) {
        return new KafkaTemplate<>(ewalletTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PaymentDto> paymentKafkaTemplate(ProducerFactory<String, PaymentDto> paymentProducer) {
        return new KafkaTemplate<>(paymentProducer);
    }

}
