package com.powerledger.challenge;

import com.powerledger.challenge.controller.AuthenticationController;
import com.powerledger.challenge.controller.BatteryController;
import com.powerledger.challenge.jwt.JwtUserDetailsService;
import com.powerledger.challenge.models.BatteryRepository;
import com.powerledger.challenge.models.UserRepository;
import com.powerledger.challenge.service.BatteryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ChallengeApplicationTests {

    @Autowired
    BatteryController batteryController;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    BatteryService batteryService;

    @Autowired
    BatteryRepository batteryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Test
    void contextLoads() {
        assertThat(batteryController).isNotNull();
        assertThat(authenticationController).isNotNull();
        assertThat(batteryService).isNotNull();
        assertThat(batteryRepository).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(userDetailsService).isNotNull();
    }

}

