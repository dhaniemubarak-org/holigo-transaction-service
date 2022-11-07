package id.holigo.services.holigotransactionservice.services.postpaid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.events.IssuedPostpaidGasEvent;
import id.holigo.services.common.events.IssuedPostpaidInsuranceEvent;
import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.common.model.gas.PostpaidGasTransactionDto;
import id.holigo.services.common.model.netv.PostpaidTvInternetTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostpaidGasTransactionServiceImpl implements PostpaidGasTransactionService{
    private JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate){this.jmsTemplate = jmsTemplate;}

    @Override
    public void issuedTransaction(PostpaidGasTransactionDto postpaidGasTransactionDto) {
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_POSTPAID_GAS_BY_ID,
                new IssuedPostpaidGasEvent(postpaidGasTransactionDto));
    }

    @Transactional
    public DetailProductTransaction sendDetailProduct(Long productId) throws JMSException {
        DetailProductTransaction productTransaction = DetailProductTransaction.builder().id(productId).build();
        Message message = jmsTemplate.sendAndReceive(JmsConfig.GET_DETAIL_TRANSACTION_GAS, session -> {
            Message message1;
            try {
                message1 = session.createTextMessage(objectMapper.writeValueAsString(productTransaction));
                message1.setStringProperty("_type", "id.holigo.services.common.model.DetailProductTransaction");
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
