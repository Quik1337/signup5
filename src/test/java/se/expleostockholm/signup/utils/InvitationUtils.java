package se.expleostockholm.signup.utils;

import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.expleostockholm.signup.utils.UserUtils.assertUsersAreEqual;

public class InvitationUtils {

    public static Invitation createMockInvitation(Event expectedEvent, User guest) {
        return Invitation.builder()
                .event_id(expectedEvent.getId())
                .guest(guest)
                .build();
    }

    public static Invitation createMockInvitation(User guest) {
        return Invitation.builder()
                .guest(guest)
                .build();
    }

    public static void assertInvitationsAreEqual(Invitation expectedInvitation, Invitation actualInvitation) {
        assertAll(
                () -> assertEquals(expectedInvitation.getAttendance(), actualInvitation.getAttendance(), "Attendance did not match!"),
                () -> assertUsersAreEqual(expectedInvitation.getGuest(), actualInvitation.getGuest(), "Guest")
        );
    }

    public static void assertInvitationListsAreEqual(List<Invitation> expectedInvitationList, List<Invitation> actualInvitationList) {
        assertEquals(expectedInvitationList.size(), actualInvitationList.size(), "Number of invitations did not match!");

        if (expectedInvitationList.size() > 1 && actualInvitationList.size() > 1) {
            Collections.sort(expectedInvitationList, COMPARE_BY_GUEST_EMAIL);
            Collections.sort(actualInvitationList, COMPARE_BY_GUEST_EMAIL);
        }

        for (int i = 0; i < expectedInvitationList.size(); i++) {
            assertInvitationsAreEqual(expectedInvitationList.get(i), actualInvitationList.get(i));
        }
    }

    public static final Comparator<Invitation> COMPARE_BY_GUEST_EMAIL = Comparator.comparing(invitation -> invitation.getGuest().getEmail());
}
