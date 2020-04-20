package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Account;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.service.AccountService;
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
    private final AccountService accountService;

    // User  ---------------------------------------------------------------------------------------------------------

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public User getUserByEmail(String email) throws InterruptedException {
        return userService.getUserByEmail(email);}

    public User getUserById(Long id) {
        return userService.getUserById(id);
    }

    // Account  --------------------------------------------------------------------------------------------------------

    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    public Account getAccountById(Long id) { return accountService.getAccountById(id); }

    public Account getAccountByUserEmail(String userEmail) { return accountService.getAccountByUserEmail(userEmail); }

    // Event  ----------------------------------------------------------------------------------------------------------

    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    public Event getEventById(Long id) {
        return eventService.getEventById(id);
    }

    public List<Event> getEventsByHostId(Long id) {
        return eventService.getEventsByHostId(id);
    }

    // Invitation  -----------------------------------------------------------------------------------------------------

    public List<Invitation> getAllInvitations() {
        return invitationService.getAllInvitations();
    }

    public Invitation getInvitationById(Long id) {
        return invitationService.getInvitationById(id);
    }

    public List<Invitation> getInvitationsByGuestId(Long id) {
        return invitationService.getInvitationsByGuestId(id);
    }

    public List<Invitation> getInvitationsByEventId(Long id) {
        return invitationService.getInvitationsByEventId(id);
    }

}
