package com.kaluzny.demo.services;

import com.kaluzny.demo.domain.Automobile;
import org.springframework.http.ResponseEntity;

public interface JMSPublisher {

    ResponseEntity<Automobile> pushMessage(Automobile automobile);

    ResponseEntity<Automobile> updateAutoMessage(Automobile automobile);
}
