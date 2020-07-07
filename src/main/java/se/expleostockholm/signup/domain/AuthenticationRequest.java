package se.expleostockholm.signup.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    private String username;
    private String password;

}
