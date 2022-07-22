package id.holigo.services.holigotransactionservice.component;

import javax.jms.JMSException;

import id.holigo.services.holigotransactionservice.sender.*;
import id.holigo.services.holigotransactionservice.services.airlines.AirlinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductRoute {

    private final AirlinesService airlinesService;

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

    @Autowired
    private final ProductCreditcard productCreditcard;

    @Autowired
    private final ProductHotel productHotel;

    @Transactional
    public Object getDetailProduct(String transactionType, Long id, String locale) throws JMSException {
        Object fetchData = null;
        log.info("Transaction Type -> {}", transactionType);
        switch (transactionType) {
            case "PULSA":
            case "PUL":
            case "PD":
            case "PR":
                fetchData = productPulsa.sendDetailProduct(id, locale).getDetail();
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
                break;
            case "INS":
                fetchData = productInsurance.sendDetailProduct(id).getDetail();
                break;
            case "MFN":
                fetchData = productMultifinance.sendDetailProduct(id).getDetail();
                break;
            case "TLP":
                fetchData = productTelephone.sendDetailProduct(id).getDetail();
                break;
            case "CC":
                fetchData = productCreditcard.sendDetailProduct(id).getDetail();
                break;

            case "HTL":
                fetchData = productHotel.sendDetailProduct(id).getDetail();
                break;
            case "AIR":
                fetchData = airlinesService.getTransaction(id);
                break;
        }

        log.info("Get Fetch Data -> {}", fetchData);
        return fetchData;
    }
}
