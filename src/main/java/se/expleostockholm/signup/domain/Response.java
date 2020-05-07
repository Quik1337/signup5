package se.expleostockholm.signup.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private Long id;
    private String message;
}
