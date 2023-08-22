package com.powerledger.challenge.domains;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatteryDomain {
    private long id;
    private String name;
    private Integer postcode;
    private Integer capacity;
}
