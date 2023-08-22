package com.powerledger.challenge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Battery {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private Integer postcode;

    private Integer capacity;
}
