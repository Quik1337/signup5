package se.expleostockholm.signup.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Long id;
    private Long host_id; //Gör om till host_id
    private String title;
    private String description;
    private LocalDate date_of_event;
    private LocalTime time_of_event;
    private String location;
    @Builder.Default
    private List<Invitation> invitations = new ArrayList<>();
}
