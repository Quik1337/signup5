package se.expleostockholm.signup.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Account {

    private Long id;
    private User user;
    private String password;
}
