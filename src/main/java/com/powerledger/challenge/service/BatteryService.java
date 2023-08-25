package com.powerledger.challenge.service;

import com.powerledger.challenge.domains.BatteryDomain;
import com.powerledger.challenge.domains.BatteryResponseByPostcode;
import com.powerledger.challenge.domains.BatterySaveRequest;
import com.powerledger.challenge.domains.CapacityUpdateType;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface BatteryService {
    void saveRequest(BatterySaveRequest model);

    boolean isExist(Long id);
    @Cacheable(value = "batteriesByPostcode", key = "{#minPostcode, #maxPostcode}", unless = "#result != null")
    BatteryResponseByPostcode getBatteriesByPostcode(int minPostcode, int maxPostcode);

    @Cacheable(value = "batteries", key = "{#name, #postcode}", unless = "#result != null")
    List<BatteryDomain> findBatteries(String name, Integer postcode);

    void updateCapacity(Long id, CapacityUpdateType updateType, int amount);
}
