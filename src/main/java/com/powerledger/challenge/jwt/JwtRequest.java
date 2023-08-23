package com.powerledger.challenge.jwt;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtRequest implements Serializable {
    private String username;
    private String password;
}
