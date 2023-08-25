package com.powerledger.challenge.controller;

import com.powerledger.challenge.domains.BatteryDomain;
import com.powerledger.challenge.domains.BatteryResponseByPostcode;
import com.powerledger.challenge.domains.BatterySaveRequest;
import com.powerledger.challenge.domains.CapacityUpdateType;
import com.powerledger.challenge.service.BatteryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @ApiOperation("Get the batteries.")
    public ResponseEntity<List<BatteryDomain>> findBatteries(@RequestParam(name = "name", required = false) String name,
                                                             @RequestParam(name = "postcode", required = false) Integer postcode){
       List<BatteryDomain> response = service.findBatteries(name, postcode);
        return response.size() > 0 ? ResponseEntity.status(HttpStatus.OK).body(response) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/postcodes")
    @ApiOperation("Filter Batteries with postcode range")
    public ResponseEntity<BatteryResponseByPostcode> getBatteriesByPostcode(@RequestParam(name = "min") int minPostcode, @RequestParam("max") int maxPostcode){
        BatteryResponseByPostcode response = service.getBatteriesByPostcode(minPostcode, maxPostcode);
        return response.getTotalBatteries() > 0 ? ResponseEntity.status(HttpStatus.OK).body(response) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/capacity")
    @ApiOperation("Update capacity for individual battery")
    public ResponseEntity<Void> updateCapacity(@PathVariable(name = "id") Long id,
                                               @RequestParam("updateType") CapacityUpdateType updateType,
                                               @RequestParam(name = "amount") int amount){
        if(!service.isExist(id)){
            return ResponseEntity.notFound().build();
        }
        if(amount <= 0){
            return ResponseEntity.badRequest().build();
        }
        service.updateCapacity(id, updateType, amount);
        return ResponseEntity.accepted().build();
    }
}
