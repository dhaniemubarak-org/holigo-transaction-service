package id.holigo.services.holigotransactionservice.common;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import id.holigo.services.holigotransactionservice.sender.ProductPLNPRE;
import id.holigo.services.holigotransactionservice.sender.ProductPulsa;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductRoute {

    @Autowired
    private final ProductPulsa productPulsa;

    @Autowired
    private final ProductPLNPRE productPln;

    public Object getDetailProduct(String transactionType, Long id) throws JMSException {
        Object fetchData = null;
        switch (transactionType) {
            case "PULSA":
                fetchData = productPulsa.sendDetailProduct(id);
                break;

            case "PRA":
                fetchData = productPln.sendDetailProduct(id).getDetail();
                break;
        }

        return fetchData;
    }
}
