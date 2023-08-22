package com.powerledger.challenge.controller;

import com.powerledger.challenge.domains.BatterySaveRequest;
import com.powerledger.challenge.service.BatteryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/batteries")
public class BatteryController {

    private final BatteryService service;

    public BatteryController(BatteryService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @ApiOperation("Save battery entries")
    public ResponseEntity<Void> createBooking(@RequestBody BatterySaveRequest model) {
        service.saveRequest(model);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
