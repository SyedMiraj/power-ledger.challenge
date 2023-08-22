package com.powerledger.challenge.domains;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatterySaveRequest {
    private List<BatteryDomain> batteries;
}
