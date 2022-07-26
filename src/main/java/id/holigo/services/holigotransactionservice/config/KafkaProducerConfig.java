package id.holigo.services.holigotransactionservice.config;

import id.holigo.services.common.model.PaymentDto;
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
    public ProducerFactory<String, PostpaidCreditcardTransactionDto> ccTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, PrepaidElectricitiesTransactionDto> prepaidElectricityTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, PostpaidElectricitiesTransactionDto> postpaidElectricityTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, PrepaidGameTransactionDto> gameTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, PostpaidInsuranceTransactionDto> insuranceTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, PostpaidMultifinanceTransactionDto> multiFinanceTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, PostpaidTvInternetTransactionDto> tvInetTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, PostpaidPdamTransactionDto> pdamTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, PostpaidTelephoneTransactionDto> telephoneTransactionProducer() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public ProducerFactory<String, HotelTransactionDto> hotelTransactionProducer() {
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
    public KafkaTemplate<String, PostpaidCreditcardTransactionDto> ccKafkaTemplate(ProducerFactory<String, PostpaidCreditcardTransactionDto> ccTransactionProducer) {
        return new KafkaTemplate<>(ccTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PrepaidElectricitiesTransactionDto> prepaidElectricityKafkaTemplate(ProducerFactory<String, PrepaidElectricitiesTransactionDto> prepaidElectricityTransactionProducer) {
        return new KafkaTemplate<>(prepaidElectricityTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PostpaidElectricitiesTransactionDto> postpaidElectricityKafkaTemplate(ProducerFactory<String, PostpaidElectricitiesTransactionDto> postpaidElectricityTransactionProducer) {
        return new KafkaTemplate<>(postpaidElectricityTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PrepaidGameTransactionDto> gameKafkaTemplate(ProducerFactory<String, PrepaidGameTransactionDto> gameTransactionProducer) {
        return new KafkaTemplate<>(gameTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PostpaidInsuranceTransactionDto> insuranceKafkaTemplate(ProducerFactory<String, PostpaidInsuranceTransactionDto> insuranceTransactionProducer) {
        return new KafkaTemplate<>(insuranceTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PostpaidMultifinanceTransactionDto> multiFinanceKafkaTemplate(ProducerFactory<String, PostpaidMultifinanceTransactionDto> multiFinanceTransactionProducer) {
        return new KafkaTemplate<>(multiFinanceTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PostpaidTvInternetTransactionDto> tvInetKafkaTemplate(ProducerFactory<String, PostpaidTvInternetTransactionDto> tvInetTransactionProducer) {
        return new KafkaTemplate<>(tvInetTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PostpaidPdamTransactionDto> pdamKafkaTemplate(ProducerFactory<String, PostpaidPdamTransactionDto> pdamTransactionProducer) {
        return new KafkaTemplate<>(pdamTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PostpaidTelephoneTransactionDto> telephoneKafkaTemplate(ProducerFactory<String, PostpaidTelephoneTransactionDto> telephoneTransactionProducer) {
        return new KafkaTemplate<>(telephoneTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, HotelTransactionDto> hotelKafkaTemplate(ProducerFactory<String, HotelTransactionDto> hotelTransactionProducer) {
        return new KafkaTemplate<>(hotelTransactionProducer);
    }

    @Bean
    public KafkaTemplate<String, PaymentDto> paymentKafkaTemplate(ProducerFactory<String, PaymentDto> paymentProducer) {
        return new KafkaTemplate<>(paymentProducer);
    }

}
