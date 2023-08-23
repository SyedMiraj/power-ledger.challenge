package com.powerledger.challenge.models;

import org.springframework.data.jpa.domain.Specification;

public class BatterySpecification {

    public static Specification<Battery> findBatteriesWithSpecification(int minPostcode, int maxCode){
        return (root, query, cb) -> cb.between(root.get("postcode"), minPostcode, maxCode);
    }
}
