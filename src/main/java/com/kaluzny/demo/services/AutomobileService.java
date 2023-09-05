package com.kaluzny.demo.services;

import com.kaluzny.demo.domain.Automobile;

public interface AutomobileService {

    Automobile refreshAutomobile(Long id, Automobile automobile);
}
