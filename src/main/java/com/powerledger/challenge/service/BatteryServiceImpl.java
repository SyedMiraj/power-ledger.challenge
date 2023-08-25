package com.powerledger.challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.powerledger.challenge.domains.*;
import com.powerledger.challenge.models.Battery;
import com.powerledger.challenge.models.BatteryRepository;
import com.powerledger.challenge.models.BatterySpecification;
import com.powerledger.challenge.models.mapper.BatteryMapper;
import com.powerledger.challenge.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatteryServiceImpl implements BatteryService{

    private static final Logger logger = LoggerFactory.getLogger(BatteryServiceImpl.class);
    private final BatteryRepository repository;
    private final BatteryMapper mapper;

    private final KafkaTemplate<String, String > template;

    public BatteryServiceImpl(BatteryRepository repository, BatteryMapper mapper, KafkaTemplate<String, String> template) {
        this.repository = repository;
        this.mapper = mapper;
        this.template = template;
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
    public boolean isExist(Long id) {
        return repository.findById(id).isPresent();
    }

    @Override
    public BatteryResponseByPostcode getBatteriesByPostcode(int minPostcode, int maxPostcode) {
        logger.info("Filtering batteries with postcode. Min postcode={}, and Max postcode={}", minPostcode, maxPostcode);
        List<Battery> batteries = repository.findAll(BatterySpecification.findBatteriesWithSpecification(null, null, minPostcode, maxPostcode));
        List<BatteryDomain> list = batteries.stream()
                .map(mapper::modelToDomain)
                .sorted()
                .collect(Collectors.toList());
        BatteryResponseByPostcode response = new BatteryResponseByPostcode();
        response.setBatteries(list);
        return summary(response);
    }

    @Override
    public List<BatteryDomain> findBatteries(String name, Integer postcode) {
        List<Battery> batteries = repository.findAll(BatterySpecification.findBatteriesWithSpecification(name, postcode, null, null));
        return batteries.stream()
                .map(mapper::modelToDomain)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public void updateCapacity(Long id, CapacityUpdateType updateType, int amount) {
        logger.info("Updating battery capacity for id={}", id);
        Battery battery = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Batter not found with id " + id));
        Integer updatedCapacity = updateType.compareTo(CapacityUpdateType.increase) == 0 ? (battery.getCapacity() + amount): (battery.getCapacity() - amount);
        battery.setCapacity(updatedCapacity);
        repository.save(battery);
        String message = updateType.compareTo(CapacityUpdateType.increase) == 0 ? ("Capacity increased for Battery with id=" + id) : ("Capacity decreased for Battery with id=" + id);
        logger.info(message);
        CapacityInfoMessage infoMessage = new CapacityInfoMessage(id, message);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(infoMessage);
            template.send(Constants.TOPIC_NAME, json);
            logger.info("capacity update message send to kafka server successfully");
        } catch (JsonProcessingException e) {
            logger.error("Exception in object parsing. Message not published to Kafka server");
        }
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
