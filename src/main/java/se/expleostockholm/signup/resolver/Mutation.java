package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Response;
import se.expleostockholm.signup.service.EventService;
import se.expleostockholm.signup.service.InvitationService;

@Component
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private final EventService eventService;
    private final InvitationService invitationService;


    /**
     * Method for updating an invited Guest's Attendance status.
     * <p>
     * Accepts an Attendance and Invitation Id arguments for updating the Attendance status and returns
     * a Response if the Attendance was successfully update or not.
     *
     * @param   attendance      new Attendance status to be updated
     * @param   invitation_id   Id for the Invitation
     */
    public Response setAttendance(Attendance attendance, Long invitation_id) {
        invitationService.setAttendance(attendance, invitation_id);

        return Response.builder()
                .message("Attendance was successfully updated!")
                .build();
    }

    /**
     * Method for creating an Event.
     *<p>
     * Accepts an Event as an argument from the frontend and returns a Response whether Event was successfully
     * saved or not.
     *
     * @param   event   an Event with values coming from the frontend
     * @return          a Response with info if Event was saved or not
     */
    public Response createEvent(Event event) {
        eventService.createNewEvent(event);

        return Response.builder()
                .message("Event was successfully saved!")
                .id(event.getId())
                .build();
    }
}
