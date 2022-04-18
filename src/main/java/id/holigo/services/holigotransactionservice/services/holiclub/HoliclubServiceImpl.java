package id.holigo.services.holigotransactionservice.services.holiclub;

import id.holigo.services.common.events.HoliclubEvent;
import id.holigo.services.common.model.IncrementUserClubDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class HoliclubServiceImpl implements HoliclubService {


    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void incrementUserClub(IncrementUserClubDto incrementUserClubDto) {
        jmsTemplate.convertAndSend(JmsConfig.INCREMENT_USERCLUB_BY_USER_ID_QUEUE, new HoliclubEvent(incrementUserClubDto));
    }
}
