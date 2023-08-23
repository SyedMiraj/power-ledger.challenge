package com.powerledger.challenge.service;

import com.powerledger.challenge.domains.BatteryDomain;
import com.powerledger.challenge.domains.BatteryResponseByPostcode;
import com.powerledger.challenge.domains.BatterySaveRequest;
import com.powerledger.challenge.models.Battery;
import com.powerledger.challenge.models.BatteryRepository;
import com.powerledger.challenge.models.BatterySpecification;
import com.powerledger.challenge.models.mapper.BatteryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.IntSummaryStatistics;
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

    @Override
    public BatteryResponseByPostcode getBatteriesByPostcode(int minPostcode, int maxPostcode) {
        List<Battery> batteries = repository.findAll(BatterySpecification.findBatteriesWithSpecification(minPostcode, maxPostcode));
        List<BatteryDomain> list = batteries.stream()
                .map(model -> mapper.modelToDomain(model))
                .sorted()
                .collect(Collectors.toList());
        BatteryResponseByPostcode response = new BatteryResponseByPostcode();
        response.setBatteries(list);
        return summary(response);
    }

    private BatteryResponseByPostcode summary(BatteryResponseByPostcode response) {
        IntSummaryStatistics statistics = new IntSummaryStatistics();
        response.getBatteries()
                .forEach(battery -> statistics.accept(battery.getCapacity()));
        response.setTotalBatteries(statistics.getCount());
        response.setAverageCapacity(statistics.getAverage());
        return response;
    }

}
