package id.holigo.services.holigotransactionservice.services.point;

import id.holigo.services.common.events.UserPointEvent;
import id.holigo.services.common.model.UpdateUserPointDto;
import id.holigo.services.holigotransactionservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PointServiceImpl implements PointService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void updateUserPoint(UpdateUserPointDto updateUserPointDto) {
        log.info("updateUserPoint is running -> {} ", updateUserPointDto);
        jmsTemplate.convertAndSend(JmsConfig.UPDATE_POINT_BY_USER_ID_QUEUE, new UserPointEvent(updateUserPointDto));
    }
}
