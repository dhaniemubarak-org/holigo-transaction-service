package id.holigo.services.holigotransactionservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {
    public static final String CREATE_NEW_TRANSACTION = "create-new-transaction";
    public static final String GET_TRANSACTION_BY_ID = "get-transaction-by-id";
    public static final String DETAIL_PRODUCT_PULSA = "get-detail-pulsa";
    public static final String DETAIL_PRODUCT_PLNPRE = "get-detail-plnpre";
    public static final String ISSUED_PREPAID_ELECTRICITIES_BY_ID = "issued-prepaid-electricities-by-id";
    public static final String ISSUED_POSTPAID_PDAM_BY_ID = "issued-postpaid-pdam-by-id";
    public static final String SET_ORDER_STATUS_BY_TRANSACTION_ID_TYPE = "set-order-status-transaction-by-transaction-id-type";
    public static final String ISSUED_TRANSACTION_BY_ID = "issued-transaction-by-id";
    public static final String SET_PAYMENT_IN_TRANSACTION_BY_ID = "set-payment-in-transaction-by-id";
    public static final String UPDATE_PAYMENT_STATUS_BY_PAYMENT_ID = "update-payment-status-by-payment-id";
    public static final String DETAIL_PRODUCT_GAME = "get-detail-game";
    public static final String GET_DETAIL_TRANSACTION_PDAM = "get-detail-pdam";
    public static final String DETAIL_PRODUCT_PLNPAS = "get-detail-pln-pas";

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
