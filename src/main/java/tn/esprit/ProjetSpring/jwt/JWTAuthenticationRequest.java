package tn.esprit.ProjetSpring.jwt;

import lombok.Data;

@Data
public class JWTAuthenticationRequest {
    private String email;
    private String password;
}
