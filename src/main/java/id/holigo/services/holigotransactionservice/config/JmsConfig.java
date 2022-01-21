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
    public static final String SET_ORDER_STATUS_TRANSACTION_BY_TRANSACTION_ID = "set-order-status-transaction-by-transaction-id";

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
