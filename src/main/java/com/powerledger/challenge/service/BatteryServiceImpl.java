package com.powerledger.challenge.service;

import com.powerledger.challenge.domains.BatterySaveRequest;
import com.powerledger.challenge.models.Battery;
import com.powerledger.challenge.models.BatteryRepository;
import com.powerledger.challenge.models.mapper.BatteryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatteryServiceImpl implements BatteryService{

    private static final Logger logger = LoggerFactory.getLogger(BatteryService.class);
    private final BatteryRepository repository;
    private final BatteryMapper mapper;

    public BatteryServiceImpl(BatteryRepository repository, BatteryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Async
    @Override
    public void saveRequest(BatterySaveRequest model) {
        logger.info("Total incoming battery request= {}", model.getBatteries() != null ? model.getBatteries().size() : 0);
        if(model.getBatteries() != null && !model.getBatteries().isEmpty()){
            List<Battery> batteries = model.getBatteries().stream()
                    .map(mapper::domainToModel)
                    .collect(Collectors.toList());
            repository.saveAll(batteries);
        }
    }


}
