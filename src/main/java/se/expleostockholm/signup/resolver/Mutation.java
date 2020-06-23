package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.*;
import se.expleostockholm.signup.service.*;

@Component
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private final UserService userService;
    private final EventService eventService;
    private final InvitationService invitationService;

    // User  -----------------------------------------------------------------------------------------------------------

    public Response registerUser(User user) throws Exception {
        userService.registerUser(user);

        return Response.builder()
                .message("The user was successfully been registered.")
                .id(user.getId())
                .build();
    }

    // Event  ----------------------------------------------------------------------------------------------------------

    public Response createEvent(Event event) throws Exception {
        eventService.createEvent(event);

        return Response.builder()
                .message("The event was successfully created.")
                .id(event.getId())
                .build();
    }

    // Invitation  -----------------------------------------------------------------------------------------------------

    public Response createInvitation(Invitation invitation) throws Exception {
        invitationService.createInvitation(invitation);

        return Response.builder()
                .message("The invitation was successfully created.")
                .id(invitation.getId())
                .build();
    }


    public Response updateInvitation(Invitation invitation) {
        invitationService.updateInvitation(invitation);

        return Response.builder()
                .message("The invitation was successfully updated.")
                .id(invitation.getId())
                .build();
    }

    public Response deleteInvitation(Invitation invitation) {
        invitationService.deleteInvitation(invitation);

        return Response.builder()
                .message("The invitation was successfully deleted.")
                .id(invitation.getId())
                .build();
    }

}
