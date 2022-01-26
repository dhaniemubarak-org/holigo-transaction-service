package id.holigo.services.holigotransactionservice.component;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import id.holigo.services.holigotransactionservice.sender.ProductGame;
import id.holigo.services.holigotransactionservice.sender.ProductPLNPAS;
import id.holigo.services.holigotransactionservice.sender.ProductPLNPRE;
import id.holigo.services.holigotransactionservice.sender.ProductPdam;
import id.holigo.services.holigotransactionservice.sender.ProductPulsa;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductRoute {

    @Autowired
    private final ProductPulsa productPulsa;

    @Autowired
    private final ProductPLNPRE productPln;

    @Autowired
    private final ProductPdam productPdam;

    @Autowired
    private final ProductGame productGame;

    @Autowired
    private final ProductPLNPAS productPLNPAS;

    public Object getDetailProduct(String transactionType, Long id) throws JMSException {
        Object fetchData = null;
        switch (transactionType) {
            case "PULSA":
            case "PUL":
            case "PD":
            case "PR":
                fetchData = productPulsa.sendDetailProduct(id);
                break;

            case "PRA":
                fetchData = productPln.sendDetailProduct(id).getDetail();
                break;

            case "PAM":
                fetchData = productPdam.sendDetailProduct(id).getDetail();
                break;

            case "GAME":
                fetchData = productGame.sendDetailProduct(id).getDetail();
                break;

            case "PAS":
                fetchData = productPLNPAS.sendDetalProduct(id).getDetail();
                break;

        }

        return fetchData;
    }
}
