package com.powerledger.challenge.domains;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class BatteryResponseByPostcode {
    private long totalBatteries;
    private double averageCapacity;

    List<BatteryDomain> batteries;
}
