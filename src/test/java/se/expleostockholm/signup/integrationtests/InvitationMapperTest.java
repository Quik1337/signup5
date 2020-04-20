package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.UserMapper;
import se.expleostockholm.signup.utils.InvitationUtils;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static se.expleostockholm.signup.utils.InvitationUtils.assertInvitationsAreEqual;


@TestMethodOrder(OrderAnnotation.class)
@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class InvitationMapperTest extends SignupDbTests {

    @Resource
    private InvitationMapper invitationMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private EventMapper eventMapper;

    private final Long eventId = 10L;
    private final Long guestId = 50L;

    private Invitation expectedInvitation;

    public void tearDown() {
        invitationMapper.removeInvitationByEventId(eventId);
    }

    @Test
    @Order(1)
    void invitation_exists_success() {
        Optional<Invitation> invitation = invitationMapper.getInvitationById(2L);

        assertAll(
                () -> assertTrue(invitation.isPresent(), "No invitation found!"),
                () -> assertEquals(2L, invitation.get().getEvent_id(), "Event id did not match!"),
                () -> assertEquals(30L, invitation.get().getGuest().getId(), "Guest id did not match!"),
                () -> assertEquals(Attendance.NOT_ATTENDING, invitation.get().getAttendance(), "Attendance did not match!")
        );
    }

    @Test
    @Order(2)
    void attendance_updated_success() {
        invitationMapper.setAttendance(Attendance.MAYBE, 1L);
        Optional<Invitation> invitation = invitationMapper.getInvitationById(1L);

        assertAll(
                () -> assertTrue(invitation.isPresent(), "No invitation found!"),
                () -> assertEquals(Attendance.MAYBE, invitation.get().getAttendance(), "Attendance did not match!")
        );
    }

    @Test
    @Order(3)
    void invitation_saved_success() {
        User guest = userMapper.getUserById(guestId).get();

        Event event = eventMapper.getEventById(eventId).get();
        expectedInvitation = InvitationUtils.createMockInvitation(event, guest);

        invitationMapper.saveInvitation(expectedInvitation);
        invitationMapper.saveInvitation(expectedInvitation);
        invitationMapper.saveInvitation(expectedInvitation);
        Invitation actual_invitation = invitationMapper.getInvitationById(expectedInvitation.getId()).get();

        assertInvitationsAreEqual(expectedInvitation, actual_invitation);
        tearDown();
    }
}
