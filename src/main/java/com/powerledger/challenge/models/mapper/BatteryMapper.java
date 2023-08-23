package com.powerledger.challenge.models.mapper;


import com.powerledger.challenge.domains.BatteryDomain;
import com.powerledger.challenge.models.Battery;
import com.powerledger.challenge.models.BatteryRepository;
import org.springframework.stereotype.Component;

@Component
public class BatteryMapper {

    private final BatteryRepository repository;

    public BatteryMapper(BatteryRepository repository) {
        this.repository = repository;
    }

    public Battery domainToModel(BatteryDomain domain){
        return repository.findById(domain.getId())
                .orElseGet(Battery::new)
                .setName(domain.getName())
                .setPostcode(domain.getPostcode())
                .setCapacity(domain.getCapacity());
    }

    public BatteryDomain modelToDomain(Battery model){
        BatteryDomain domain = new BatteryDomain();
        domain.setId(model.getId());
        domain.setName(model.getName());
        domain.setPostcode(model.getPostcode());
        domain.setCapacity(model.getCapacity());
        return domain;
    }
}
