package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvitationService {

    private final InvitationEmailService invitationEmailService;
    private final InvitationMapper invitationMapper;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    public Invitation getInvitationById(Long id) throws Exception {
        return invitationMapper.getInvitationById(id).orElseThrow(() -> new Exception("No invitation found!"));
    }

    public List<Invitation> getInvitationsByEventId(Long event_id) throws Exception {

        Optional<Event> optionalEvent = eventMapper.getEventById(event_id);
        if(optionalEvent.isEmpty())
            throw new Exception("No event found with this id.");

        List<Invitation> invitations = invitationMapper.getInvitationsByEventId(event_id);
        if (invitations.size() == 0)
            throw new Exception("No invitations found for this event.");

        return invitations;
    }

    public List<Invitation> getInvitationsByGuestId(Long guest_id) throws Exception {

        List<Invitation> invitations = invitationMapper.getInvitationsByGuestId(guest_id);
        if (invitations.size() == 0) {
            throw new Exception("No invitations found for this guest!");
        }
        return invitations;
    }

    public void createInvitation(Invitation invitation) throws Exception {

        Optional<User> optionalUser = userMapper.getUserByEmail(invitation.getGuest().getEmail());

        Optional<Event> optionalEvent = eventMapper.getEventById(invitation.getEvent().getId());

        if (optionalUser.isEmpty()) {
            userMapper.createUser(invitation.getGuest());
            optionalUser = userMapper.getUserByEmail(invitation.getGuest().getEmail());
        }

        optionalUser.ifPresent(invitation::setGuest);

        if (optionalEvent.isPresent()) {
            for (Invitation tempInvitation : optionalEvent.get().getInvitations())
                if (optionalUser.isPresent())
                    if (tempInvitation.getGuest().getEmail().equals(optionalUser.get().getEmail()))
                        throw new Exception("A guest can only be invited to en event once.");
        }

        if (optionalEvent.isEmpty())
            throw new Exception("No event with that id found.");
        
        invitationMapper.createInvitation(invitation);

        invitationEmailService.sendEmail(invitation);
    }

    public void updateInvitation(Invitation invitation) {
        invitationMapper.updateInvitation(invitation);
    }

    public void deleteInvitation(Invitation invitation) {
        invitationMapper.deleteInvitation(invitation);
    }

}
