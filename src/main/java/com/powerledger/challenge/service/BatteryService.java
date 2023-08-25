package com.powerledger.challenge.service;

import com.powerledger.challenge.domains.BatteryDomain;
import com.powerledger.challenge.domains.BatteryResponseByPostcode;
import com.powerledger.challenge.domains.BatterySaveRequest;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface BatteryService {
    void saveRequest(BatterySaveRequest model);

    BatteryResponseByPostcode getBatteriesByPostcode(int minPostcode, int maxPostcode);

    @Cacheable(value = "batteries", key = "{#name, #postcode}")
    List<BatteryDomain> findBatteries(String name, Integer postcode);
}
