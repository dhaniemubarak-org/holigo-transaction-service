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

    private final ProductPulsa productPulsa;

    private final ProductPLNPRE productPln;

    private final ProductPdam productPdam;

    private final ProductGame productGame;

    private final ProductPLNPAS productPLNPAS;

    private final ProductEwallet productEwallet;

    private final ProductNetv productNetv;

    private final ProductInsurance productInsurance;

    private final ProductMultifinance productMultifinance;

    private final ProductTelephone productTelephone;

    private final ProductCreditcard productCreditcard;

    private final ProductHotel productHotel;

    @Transactional
    public Object getDetailProduct(String transactionType, Long id, String locale) throws JMSException {
        Object fetchData = null;
        log.info("Transaction Type -> {}", transactionType);
        switch (transactionType) {
            case "PUL", "PD", "PR" -> fetchData = productPulsa.sendDetailProduct(id, locale).getDetail();
            case "PRA" -> fetchData = productPln.sendDetailProduct(id).getDetail();
            case "PAM" -> fetchData = productPdam.sendDetailProduct(id).getDetail();
            case "GAME" -> fetchData = productGame.sendDetailProduct(id).getDetail();
            case "PAS" -> fetchData = productPLNPAS.sendDetalProduct(id).getDetail();
            case "EWAL", "DWAL" -> fetchData = productEwallet.sendDetailProduct(id).getDetail();
            case "NETV" -> fetchData = productNetv.sendDetailProduct(id).getDetail();
            case "INS" -> fetchData = productInsurance.sendDetailProduct(id).getDetail();
            case "MFN" -> fetchData = productMultifinance.sendDetailProduct(id).getDetail();
            case "TLP" -> fetchData = productTelephone.sendDetailProduct(id).getDetail();
            case "CC" -> fetchData = productCreditcard.sendDetailProduct(id).getDetail();
            case "HTL" -> fetchData = productHotel.sendDetailProduct(id).getDetail();
            case "AIR" -> fetchData = airlinesService.getTransaction(id);
        }

        log.info("Get Fetch Data -> {}", fetchData);
        return fetchData;
    }
}
