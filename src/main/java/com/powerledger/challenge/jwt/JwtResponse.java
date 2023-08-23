package com.powerledger.challenge.jwt;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
