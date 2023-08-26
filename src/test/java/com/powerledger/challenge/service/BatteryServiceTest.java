package com.powerledger.challenge.service;

import com.powerledger.challenge.domains.BatteryDomain;
import com.powerledger.challenge.models.Battery;
import com.powerledger.challenge.models.BatteryRepository;
import com.powerledger.challenge.models.BatterySpecification;
import com.powerledger.challenge.models.mapper.BatteryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatteryServiceTest {

    @Mock
    BatteryRepository batteryRepository;

    @Mock
    BatteryMapper batteryMapper;

    BatteryService batteryService;
    Battery battery1, battery2;


    @BeforeEach
    void beforeEach() {
        this.batteryService = new BatteryServiceImpl(batteryRepository, batteryMapper, null);
        battery1 = Battery.builder()
                .id(1L)
                .name("Test Battery-1")
                .postcode(5555)
                .capacity(2500)
                .build();
        battery2 = Battery.builder()
                .id(2L)
                .name("Test Battery-2")
                .postcode(6666)
                .capacity(1800)
                .build();
    }

    @Test
    public void test_isExist(){
        when(batteryRepository.findById(1L)).thenReturn(Optional.of(battery1));
        boolean actual = batteryService.isExist(1L);
        assertTrue(actual);
    }

    @Test
    public void test_findBatteriesByName(){
        String name = "Test Battery-1";
        Specification<Battery> specification = BatterySpecification.findBatteriesWithSpecification(name, null, null, null);
        lenient().when(batteryRepository.findAll(specification))
                .thenReturn(Arrays.asList(battery1));
        List<BatteryDomain> actual = batteryService.findBatteries(name, null);
        BatteryDomain expected = batteryMapper.modelToDomain(battery1);
        assertThat(actual.contains(expected));
    }

    @Test
    public void test_findBatteriesByPostcode(){
        Integer postcode = 6666;
        Specification<Battery> specification = BatterySpecification.findBatteriesWithSpecification(null, postcode, null, null);
        lenient().when(batteryRepository.findAll(specification))
                .thenReturn(Arrays.asList(battery2));
        List<BatteryDomain> actual = batteryService.findBatteries(null, postcode);
        BatteryDomain expected = batteryMapper.modelToDomain(battery2);
        assertThat(actual.contains(expected));
    }



}