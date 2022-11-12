package id.holigo.services.holigotransactionservice.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import id.holigo.services.holigotransactionservice.services.hotel.HotelServiceFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
@AllArgsConstructor
public class ProductHotel {
    @Autowired
    private final JmsTemplate jmsTemplate;

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private final HotelServiceFeignClient hotelServiceFeignClient;

    @Transactional
    public DetailProductTransaction sendDetailProduct(Long id) throws JMSException {
        DetailProductTransaction productTransaction = DetailProductTransaction.builder().id(id).build();
        Message message = jmsTemplate.sendAndReceive(JmsConfig.DETAIL_PRODUCT_HOTEL_TRANSACTION, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message message = null;
                try {
                    message = session.createTextMessage(objectMapper.writeValueAsString(productTransaction));
                    message.setStringProperty("_type", "id.holigo.services.common.model.DetailProductTransaction");
                    return message;
                } catch (JsonProcessingException e) {
                    throw new JMSException("Error Sending Detail Hotel Transaction");
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


    public Object getDetailTransaction(Long id){
        ResponseEntity<Object> responseDetailTransaction = hotelServiceFeignClient.getDetailTransaction(id);
        return responseDetailTransaction.getBody();
    }
}
