package id.holigo.services.holigotransactionservice.component;

import javax.jms.JMSException;

import id.holigo.services.holigotransactionservice.sender.*;
import id.holigo.services.holigotransactionservice.services.airlines.AirlinesService;
import id.holigo.services.holigotransactionservice.services.postpaid.PostpaidGasTransactionService;
import id.holigo.services.holigotransactionservice.services.prepaid.PrepaidStreamingTransactionService;
import id.holigo.services.holigotransactionservice.services.train.TrainService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductRoute {
    private final AirlinesService airlinesService;

    private final TrainService trainService;

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
    private final PostpaidGasTransactionService postpaidGasTransactionService;
    private final PrepaidStreamingTransactionService prepaidStreamingTransactionService;

    @Transactional
    public Object getDetailProduct(String transactionType, Long id, String locale) throws JMSException {
        Object fetchData = null;
        switch (transactionType) {
            case "PUL", "PD", "PR" -> fetchData = productPulsa.getDetailTransaction(id, locale);
            case "PRA", "PLNPRE" -> fetchData = productPln.getDetailTransaction(id);
            case "PAM" -> fetchData = productPdam.getDetailTransaction(id);
            case "GAME" -> fetchData = productGame.getDetailTransaction(id);
            case "PAS", "PLNPOST" -> fetchData = productPLNPAS.getDetailTransaction(id);
            case "EWAL", "DWAL" -> fetchData = productEwallet.getDetailTransaction(id);
            case "NETV" -> fetchData = productNetv.getDetailTransaction(id);
            case "INS" -> fetchData = productInsurance.getDetailTransaction(id);
            case "MFN" -> fetchData = productMultifinance.getDetailTransaction(id);
            case "TLP" -> fetchData = productTelephone.getDetailTransaction(id);
            case "CC" -> fetchData = productCreditcard.getDetailTransaction(id);
            case "HTL" -> fetchData = productHotel.getDetailTransaction(id);
            case "AIR" -> fetchData = airlinesService.getTransaction(id);
            case "GAS" -> fetchData = postpaidGasTransactionService.getDetailTransaction(id);
            case "STREAMING" -> fetchData = prepaidStreamingTransactionService.getDetailTransaction(id);
            case "TRAIN" -> fetchData = trainService.getTransaction(id);
        }
        return fetchData;
    }
}
