package com.powerledger.challenge.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BatteryRepository extends JpaRepository<Battery, Long>, JpaSpecificationExecutor {
}
