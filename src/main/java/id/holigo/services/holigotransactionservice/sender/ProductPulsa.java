package id.holigo.services.holigotransactionservice.sender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductPulsa {

    @Autowired
    private final JmsTemplate jmsTemplate;

    @Autowired
    private final ObjectMapper objectMapper;

    public DetailProductTransaction sendDetailProduct(Long productId) throws JMSException {
        DetailProductTransaction productTransaction = DetailProductTransaction.builder().id(productId).build();
        Message message = jmsTemplate.sendAndReceive(JmsConfig.DETAIL_PRODUCT_PULSA, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message message = null;
                try {
                    message = session.createTextMessage(objectMapper.writeValueAsString(productTransaction));
                    message.setStringProperty("_type", "id.holigo.services.common.model.DetailProductTransaction");
                    return message;
                } catch (JsonProcessingException e) {
                    throw new JMSException("Error Sending Detail Product Pulsa!");
                }
            }
        });

        DetailProductTransaction detailProduct = new DetailProductTransaction();

        try {
            detailProduct = objectMapper.readValue(message.getBody(String.class), DetailProductTransaction.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailProduct;
    }
}