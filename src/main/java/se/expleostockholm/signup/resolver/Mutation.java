package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.*;
import se.expleostockholm.signup.service.*;

import java.util.Optional;

@Component
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private final UserService userService;
    private final EventService eventService;
    private final InvitationService invitationService;
    private final AccountService accountService;

    // User  ---------------------------------------------------------------------------------------------------------

    public Response createUser(User user) {

        User createdUser = userService.createUser(user);

        return Response.builder()
                .message("User was successfully created")
                .id(createdUser.getId())
                .build();
    }

    // Account  --------------------------------------------------------------------------------------------------------

    public Response createAccount(User user, String password) {

        Account account = accountService.createAccount(user, password);

        return Response.builder()
                .message("Account was successfully created")
                .id(account.getId())
                .build();
    }

    // Event  ----------------------------------------------------------------------------------------------------------

    public Response createEvent(Event event) {
        eventService.createEvent(event);

        return Response.builder()
                .message("Event was successfully saved!")
                .id(event.getId())
                .build();
    }

    // Attendance  -----------------------------------------------------------------------------------------------------

    public Response setAttendance(Attendance attendance, Long invitation_id) {
        invitationService.setAttendance(attendance, invitation_id);

        return Response.builder()
                .message("Attendance was successfully updated!")
                .build();
    }
}
