package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.exception.InvitationAlreadyExistException;
import se.expleostockholm.signup.exception.InvitationNotFoundException;
import se.expleostockholm.signup.exception.SetAttendanceException;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvitationService {

    private final InvitationMapper invitationMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public void saveInvitations(List<Invitation> invitations, Long event_id) {
        invitations.forEach(invitation -> {
            invitation.setEvent_id(event_id);

            Optional<User> optionalUser = userMapper.getUserByEmail(invitation.getGuest().getEmail());

            if (optionalUser.isEmpty())
                invitation.setGuest(userService.createUser(invitation.getGuest()));

            invitationExists(invitation);

            invitationMapper.saveInvitation(invitation);
        });
    }

    public void invitationExists(Invitation invitation) {
        if (invitationMapper.invitationExists(invitation) == 1) {
            throw new InvitationAlreadyExistException(invitation.getGuest().getEmail() + " has already been invited to this event!");
        }
    }

    public void setAttendance(Attendance attendance, Long invitation_id) {
        if (invitationMapper.setAttendance(attendance, invitation_id) != 1) {
            throw new SetAttendanceException("Something went wrong while updating attendance.");
        }
    }

    public List<Invitation> getAllInvitations() {
        List<Invitation> invitations = invitationMapper.getAllInvitations();
        if (invitations.size() == 0) {
            throw new InvitationNotFoundException("No invitations found!");
        }
        return invitations;
    }

    public Invitation getInvitationById(Long id) {
        return invitationMapper.getInvitationById(id).orElseThrow(() -> new InvitationNotFoundException("No invitation found!"));
    }

    public List<Invitation> getInvitationsByEventId(Long id) {
        List<Invitation> invitations = invitationMapper.getInvitationsByEventId(id);
        if (invitations.size() == 0) {
            throw new InvitationNotFoundException("No invitations found for event");
        }
        return invitations;
    }

    public List<Invitation> getInvitationsByGuestId(Long id) {
        return invitationMapper.getInvitationsByGuestId(id);
    }

}
