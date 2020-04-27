package se.expleostockholm.signup.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Invitation {
    private Long id;
    private Event event;
    private User guest;
    private String comment;
    @Builder.Default
    private Attendance attendance = Attendance.NO_RESPONSE;
}
