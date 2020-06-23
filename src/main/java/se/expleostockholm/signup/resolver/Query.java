package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.service.EventService;
import se.expleostockholm.signup.service.InvitationService;
import se.expleostockholm.signup.service.UserService;

import java.util.List;

@Component
@AllArgsConstructor
public class Query implements GraphQLQueryResolver {

    private final UserService userService;
    private final EventService eventService;
    private final InvitationService invitationService;

    // User  -----------------------------------------------------------------------------------------------------------

    public List<User> getUsers() {
        return userService.getUsers();
    }

    public User getUserById(Long id) throws Exception {
        return userService.getUserById(id);
    }

    // Event  ----------------------------------------------------------------------------------------------------------

    public List<Event> getEvents(){
        return eventService.getEvents();
    }

    public Event getEventById(Long id) throws Exception {
        return eventService.getEventById(id);
    }

    public List<Event> getEventsByHostId(Long host_id) throws Exception {
        return eventService.getEventsByHostId(host_id);
    }

    // Invitation  -----------------------------------------------------------------------------------------------------

    public Invitation getInvitationById(Long id) throws Exception {
        return invitationService.getInvitationById(id);
    }

    public List<Invitation> getInvitationsByEventId(Long event_id) throws Exception {
        return invitationService.getInvitationsByEventId(event_id);
    }

    public List<Invitation> getInvitationsByGuestId(Long guest_id) throws Exception {
        return invitationService.getInvitationsByGuestId(guest_id);
    }

}
