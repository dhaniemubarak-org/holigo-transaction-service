package id.holigo.services.holigotransactionservice.component;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import id.holigo.services.holigotransactionservice.sender.ProductEwallet;
import id.holigo.services.holigotransactionservice.sender.ProductGame;
import id.holigo.services.holigotransactionservice.sender.ProductInsurance;
import id.holigo.services.holigotransactionservice.sender.ProductMultifinance;
import id.holigo.services.holigotransactionservice.sender.ProductNetv;
import id.holigo.services.holigotransactionservice.sender.ProductPLNPAS;
import id.holigo.services.holigotransactionservice.sender.ProductPLNPRE;
import id.holigo.services.holigotransactionservice.sender.ProductPdam;
import id.holigo.services.holigotransactionservice.sender.ProductPulsa;
import id.holigo.services.holigotransactionservice.sender.ProductTelephone;
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

    @Autowired
    private final ProductEwallet productEwallet;

    @Autowired
    private final ProductNetv productNetv;

    @Autowired
    private final ProductInsurance productInsurance;

    @Autowired
    private final ProductMultifinance productMultifinance;

    @Autowired
    private final ProductTelephone productTelephone;

    @Transactional
    public Object getDetailProduct(String transactionType, Long id) throws JMSException {
        Object fetchData = null;
        switch (transactionType) {
            case "PULSA":
            case "PUL":
            case "PD":
            case "PR":
                fetchData = productPulsa.sendDetailProduct(id).getDetail();
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

            case "EWAL":
            case "DWAL":
                fetchData = productEwallet.sendDetailProduct(id).getDetail();
                break;

            case "NETV":
                fetchData = productNetv.sendDetailProduct(id).getDetail();

            case "INS":
                fetchData = productInsurance.sendDetailProduct(id).getDetail();

            case "MFN":
                fetchData = productMultifinance.sendDetailProduct(id).getDetail();

            case "TLP":
                fetchData = productTelephone.sendDetailProduct(id).getDetail();
        }

        return fetchData;
    }
}
