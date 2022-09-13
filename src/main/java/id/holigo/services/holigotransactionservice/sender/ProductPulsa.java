package id.holigo.services.holigotransactionservice.sender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.transaction.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductPulsa {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    @Transactional
    public DetailProductTransaction sendDetailProduct(Long productId, String locale) throws JMSException {
        DetailProductTransaction productTransaction = DetailProductTransaction.builder().id(productId).build();
        Message message = jmsTemplate.sendAndReceive(JmsConfig.DETAIL_PRODUCT_TRANSACTION_PULSA, session -> {
            Message message1;
            try {
                message1 = session.createTextMessage(objectMapper.writeValueAsString(productTransaction));
                message1.setStringProperty("_type", "id.holigo.services.common.model.DetailProductTransaction");
                message1.setStringProperty("locale", locale);
                return message1;
            } catch (JsonProcessingException e) {
                throw new JMSException("Error Sending Detail Product Pulsa!");
            }
        });

        DetailProductTransaction detailProduct = new DetailProductTransaction();

        assert message != null;
        try {
            detailProduct = objectMapper.readValue(message.getBody(String.class), DetailProductTransaction.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detailProduct;
    }
}