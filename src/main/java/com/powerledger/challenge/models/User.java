package com.powerledger.challenge.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "PL_USER")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String username;

    @Column
    @JsonIgnore
    private String password;
}
