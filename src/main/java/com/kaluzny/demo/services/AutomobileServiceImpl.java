package com.kaluzny.demo.services;

import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.domain.AutomobileRepository;
import com.kaluzny.demo.exception.AutoWasDeletedException;
import com.kaluzny.demo.exception.ThereIsNoSuchAutoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AutomobileServiceImpl implements AutomobileService{

    private final AutomobileRepository repository;

    @Override
    public Automobile refreshAutomobile(Long id, Automobile automobile) {
        return repository.findById(id)
                .map(entity -> {
                    entity.checkColor(automobile);
                    entity.setName(automobile.getName());
                    entity.setColor(automobile.getColor());
                    entity.setUpdateDate(automobile.getUpdateDate());
                    if (entity.getDeleted()) {
                        throw new AutoWasDeletedException();
                    }
                    return repository.save(entity);
                })
                //.orElseThrow(() -> new EntityNotFoundException("Automobile not found with id = " + id));
                .orElseThrow(ThereIsNoSuchAutoException::new);
    }
}
