package com.powerledger.challenge.models;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class BatterySpecification {

    public static Specification<Battery> findBatteriesWithSpecification(String name, Integer postcode, Integer minPostcode, Integer maxCode){
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if(name != null){
                predicate = cb.and(predicate, cb.equal(root.get("name"), name));
            }
            if(postcode != null){
                predicate = cb.and(predicate, cb.equal(root.get("postcode"), postcode));
            }
            if(minPostcode != null || maxCode != null){
                if(minPostcode == null){
                    cb.lessThan(root.get("postcode"), maxCode);
                } else if (maxCode == null) {
                    cb.greaterThan(root.get("postcode"), minPostcode);
                } else {
                    cb.between(root.get("postcode"), minPostcode, maxCode);
                }
            }

        return predicate;
        };
    }
}
