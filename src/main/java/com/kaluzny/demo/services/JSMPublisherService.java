package com.kaluzny.demo.services;

import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.domain.AutomobileRepository;
import jakarta.jms.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class JSMPublisherService implements JMSPublisher {

    private final JmsTemplate jmsTemplate;
    private final AutomobileRepository repository;
    private final AutomobileService automobileService;

    @Override
    public ResponseEntity<Automobile> pushMessage(Automobile automobile) {
        try {
            Topic autoTopic = Objects.requireNonNull(jmsTemplate
                    .getConnectionFactory()).createConnection().createSession().createTopic("AutoTopic");
            Automobile savedAutomobile = repository.save(automobile);
            log.info("\u001B[32m" + "Sending Automobile with id: " + savedAutomobile.getId() + "\u001B[0m");
            jmsTemplate.convertAndSend(autoTopic, savedAutomobile);
            return new ResponseEntity<>(savedAutomobile, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Automobile> updateAutoMessage(Automobile automobile) {
        try {
            Topic autoTopic = Objects.requireNonNull(jmsTemplate
                    .getConnectionFactory()).createConnection().createSession().createTopic("UpdateAutoTopic");
            Automobile savedAutomobile = automobileService.refreshAutomobile(automobile.getId(),automobile);
            log.info("\u001B[32m" + "Sending Automobile with id: " + savedAutomobile.getId() + "\u001B[0m");
            jmsTemplate.convertAndSend(autoTopic, savedAutomobile);
            return new ResponseEntity<>(savedAutomobile, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
