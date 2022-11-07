package id.holigo.services.holigotransactionservice.services.prepaid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.events.IssuedPrepaidStreamingEvent;
import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.common.model.streaming.PrepaidStreamingTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrepaidStreamingTransactionImpl implements PrepaidStreamingTransactionService{

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public void issuedTransaction(PrepaidStreamingTransactionDto prepaidStreamingTransactionDto){
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_PREPAID_STREAMING_BY_ID,
                new IssuedPrepaidStreamingEvent(prepaidStreamingTransactionDto));
    }

    public DetailProductTransaction sendDetailProduct(Long productId) throws JMSException{
        DetailProductTransaction detailProductTransaction = DetailProductTransaction.builder().id(productId).build();
        Message message = jmsTemplate.sendAndReceive(JmsConfig.DETAIL_TRANSACTION_PRODUCT_STREAMING, session -> {
           Message message1;
           try{
               message1 = session.createTextMessage(objectMapper.writeValueAsString(detailProductTransaction));
               message1.setStringProperty("_type", "id.holigo.services.common.model.DetailProductTransaction");
               return message1;
           }catch (JsonProcessingException e){
               throw new JMSException("Error Sending Detail Transactin Streaming!1");
           }
        });
        DetailProductTransaction retrieveDetailProductTransaction = new DetailProductTransaction();
        try{
            assert message != null;
            retrieveDetailProductTransaction = objectMapper.readValue(message.getBody(String.class), DetailProductTransaction.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return retrieveDetailProductTransaction;
    }
}
