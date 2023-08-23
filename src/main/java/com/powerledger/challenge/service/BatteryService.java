package com.powerledger.challenge.service;

import com.powerledger.challenge.domains.BatteryResponseByPostcode;
import com.powerledger.challenge.domains.BatterySaveRequest;

public interface BatteryService {
    void saveRequest(BatterySaveRequest model);

    BatteryResponseByPostcode getBatteriesByPostcode(int minPostcode, int maxPostcode);
}
