package se.expleostockholm.signup.acceptancetests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.integrationtests.SignupDbTests;
import se.expleostockholm.signup.repository.InvitationMapper;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class SetAttendanceTest extends SignupDbTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Resource
    private InvitationMapper invitationMapper;

    private ObjectNode queryVariables;

    private final static Long invitation_id = 1L;
    private final static String expectedMessage_positive = "Attendance was successfully updated!";

    @ParameterizedTest
    @Order(1)
    @MethodSource("providedData")
    public void setAttendance_test(Long invitationId, Attendance attendance, String expectedMessage) throws IOException {
        queryVariables = setQueryVariables(invitationId, attendance);
        String responseMessage = when_user_responds_with_attendance(queryVariables);

        then_user_gets_response_message(responseMessage, expectedMessage);
        and_invitation_status_is_updated_to_attendance(attendance);

    }

    public String when_user_responds_with_attendance(ObjectNode variables) throws IOException {
        return graphQLTestTemplate
                .perform("mutations/setAttendance.graphql", variables)
                .get("$.data.response.message");
    }

    public void then_user_gets_response_message(String responseMessage, String expectedMessage) {
        assertEquals(expectedMessage, responseMessage);
    }

    public void and_invitation_status_is_updated_to_attendance(Attendance expectedAttendance) throws IOException {
        Attendance actualAttendance = Attendance.valueOf(graphQLTestTemplate
                .perform("queries/getInvitationById.graphql", queryVariables)
                .get("$.data.invitation.attendance"));

        assertEquals(expectedAttendance, actualAttendance);
    }

    private static Stream<Arguments> providedData() {
        return Stream.of(
                Arguments.of(invitation_id, Attendance.ATTENDING, expectedMessage_positive),
                Arguments.of(invitation_id, Attendance.NOT_ATTENDING, expectedMessage_positive),
                Arguments.of(invitation_id, Attendance.MAYBE, expectedMessage_positive),
                Arguments.of(invitation_id, Attendance.NO_RESPONSE, expectedMessage_positive)
        );
    }

    public ObjectNode setQueryVariables(Long invitationId, Attendance attendance) {
        ObjectNode queryVariables = new ObjectMapper().createObjectNode();
        queryVariables.put("attendance", attendance.toString());
        queryVariables.put("invitation_id", invitationId);

        return queryVariables;
    }

    @Test
    @Order(2)
    public void tearDown() {
        invitationMapper.setAttendance(Attendance.NO_RESPONSE, invitation_id);
    }


}
