package id.holigo.services.holigotransactionservice.services.hotel;

import id.holigo.services.common.events.IssuedHotelEvent;
import id.holigo.services.common.model.hotel.HotelTransactionDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void issuedTransaction(HotelTransactionDto hotelTransactionDto) {
        log.info("issuedTransaction is running...");
        log.info("hotelTransactionDto -> {}", hotelTransactionDto);
        jmsTemplate.convertAndSend(JmsConfig.ISSUED_HOTEL_BY_ID,
                new IssuedHotelEvent(hotelTransactionDto));
    }
}
