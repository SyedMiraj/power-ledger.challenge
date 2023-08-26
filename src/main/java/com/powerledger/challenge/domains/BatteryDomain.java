package com.powerledger.challenge.domains;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatteryDomain implements Comparable<BatteryDomain>{
    private long id;
    private String name;
    private Integer postcode;
    private Integer capacity;

    @Override
    public int compareTo(BatteryDomain batteryDomain) {
        return this.name.compareTo(batteryDomain.getName());
    }
}
