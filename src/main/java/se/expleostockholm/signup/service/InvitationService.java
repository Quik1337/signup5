package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvitationService {

    private final InvitationMapper invitationMapper;

    public Invitation getInvitationById(Long id) throws Exception {
        return invitationMapper.getInvitationById(id).orElseThrow(() -> new Exception("No invitation found!"));
    }

    public List<Invitation> getInvitationsByEventId(Long event_id) throws Exception {
        List<Invitation> invitations = invitationMapper.getInvitationsByEventId(event_id);
        if (invitations.size() == 0) {
            throw new Exception("No invitations found for this event!");
        }
        return invitations;
    }

    public List<Invitation> getInvitationsByGuestId(Long guest_id) throws Exception {

        List<Invitation> invitations = invitationMapper.getInvitationsByGuestId(guest_id);
        if (invitations.size() == 0) {
            throw new Exception("No invitations found for this guest!");
        }
        return invitations;
    }

    public Invitation updateInvitation(Invitation invitationUpdateInput) throws Exception {
        invitationMapper.updateInvitation(invitationUpdateInput);

        return invitationMapper.getInvitationById(invitationUpdateInput.getId()).orElseThrow(() -> new Exception("No invitation found!"));
    }
}
