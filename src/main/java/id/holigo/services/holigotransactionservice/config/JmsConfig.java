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
    public static final String DETAIL_PRODUCT_TRANSACTION_PULSA = "get-detail-pulsa-transaction";
    public static final String DETAIL_PRODUCT_PLNPRE = "get-detail-plnpre";
    public static final String ISSUED_PREPAID_ELECTRICITIES_BY_ID = "issued-prepaid-electricities-by-id";
    public static final String ISSUED_PREPAID_PULSA_BY_ID = "issued-prepaid-pulsa-by-id";
    public static final String ISSUED_POSTPAID_PDAM_BY_ID = "issued-postpaid-pdam-by-id";
    public static final String SET_ORDER_STATUS_BY_TRANSACTION_ID_TYPE = "set-order-status-transaction-by-transaction-id-type";
    public static final String ISSUED_TRANSACTION_BY_ID = "issued-transaction-by-id";
    public static final String SET_PAYMENT_IN_TRANSACTION_BY_ID = "set-payment-in-transaction-by-id";
    public static final String UPDATE_PAYMENT_STATUS_BY_PAYMENT_ID = "update-payment-status-by-payment-id";
    public static final String GET_DETAIL_TRANSACTION_PDAM = "get-detail-pdam";
    public static final String DETAIL_PRODUCT_PLNPAS = "get-detail-pln-pas";
    public static final String ISSUED_POSTPAID_ELECTRICITIES_BY_ID = "issued-postpaid-electricities-by-id";
    public static final String ISSUED_PREPAID_WALLET_BY_ID = "issued-prepaid-wallet-by-id";
    public static final String DETAIL_PRODUCT_WALLET_TRANSACTION = "detail-product-wallet-transaction";
    public static final String ISSUED_PREPAID_GAMES_BY_ID = "issued-prepaid-games-by-id";
    public static final String DETAIL_PRODUCT_GAME = "get-detail-game";
    public static final String ISSUED_POSTPAID_NETV_BY_ID = "issued-postpaid-netv-by-id";
    public static final String DETAIL_PRODUCT_NETV_TRANSACTION = "detail-product-netv-transaction";
    public static final String ISSUED_POSTPAID_INS_BY_ID = "issued-postpaid-ins-by-id";
    public static final String DETAIL_PRODUCT_INS_TRANSACTION = "detail-product-ins-transaction";
    public static final String ISSUED_POSTPAID_MULTIFINANCE_BY_ID = "issued-postpaid-multifinance-by-id";
    public static final String DETAIL_PRODUCT_MFN_TRANSACTION = "detail-product-mfn-transaction";
    public static final String ISSUED_POSTPAID_TLP_BY_ID = "issued-postpaid-tlp-by-id";
    public static final String DETAIL_PRODUCT_TLP_TRANSACTION = "detail-product-tlp-transaction";
    public static final String ISSUED_POSTPAID_CC_BY_ID = "issued-postpaid-cc-by-id";
    public static final String DETAIL_PRODUCT_CC_TRANSACTION = "detail-product-cc-transaction";

    public static final String INCREMENT_USERCLUB_BY_USER_ID_QUEUE = "increment-userclub-by-user-id";

    public static final String UPDATE_POINT_BY_USER_ID_QUEUE = "update-point-by-user-id-queue";

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
