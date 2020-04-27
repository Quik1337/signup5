package se.expleostockholm.signup.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String first_name;
    private String last_name;
}
