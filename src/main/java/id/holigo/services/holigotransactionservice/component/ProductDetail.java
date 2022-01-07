package id.holigo.services.holigotransactionservice.component;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import id.holigo.services.common.model.DetailProductTransaction;
import id.holigo.services.holigotransactionservice.sender.ProductPulsa;
import id.holigo.services.holigotransactionservice.web.mappers.DetailProductMapper;
import id.holigo.services.holigotransactionservice.web.model.DetailProductForUser;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductDetail {

    @Autowired
    private final ProductPulsa productPulsa;

    @Autowired
    private final DetailProductMapper detailProductMapper;

    public DetailProductForUser productSender(String transactionType, String transactionId) throws JMSException {
        DetailProductTransaction product = DetailProductTransaction.builder().id(transactionId).build();
        switch (transactionType) {
            case "PULSA":
                product = productPulsa.sendDetailProduct(product);
                break;
        }
        return detailProductMapper.detailProductTransactionToDetailProductForUser(product);
    }
}
