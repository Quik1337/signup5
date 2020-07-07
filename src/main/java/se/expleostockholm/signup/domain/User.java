package se.expleostockholm.signup.domain;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String first_name;
    private String last_name;
    private String password;
    private Role role;
}
