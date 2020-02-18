package se.expleostockholm.signup.acceptancetests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.domain.Response;
import se.expleostockholm.signup.integrationtests.SignupDbTests;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.*;
import static se.expleostockholm.signup.utils.EventUtils.createMockEvent;
import static se.expleostockholm.signup.utils.InvitationUtils.assertInvitationListsAreEqual;
import static se.expleostockholm.signup.utils.InvitationUtils.createMockInvitation;
import static se.expleostockholm.signup.utils.PersonUtils.createMockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class CreateEventTest extends SignupDbTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Resource
    private PersonMapper personMapper;

    @Resource
    private EventMapper eventMapper;

    @Resource
    private InvitationMapper invitationMapper;

    private ObjectNode eventInput;
    private ObjectNode eventVariables;
    private ObjectNode variables;

    private Event event;


    @AfterAll
    public void tearDown() {
        System.out.println("inside teardown");
        invitationMapper.removeInvitationByEventId(event.getId());
        event.getInvitations().forEach(i -> personMapper.removePersonByEmail(i.getGuest().getEmail()));
        eventMapper.removeEventById(event.getId());
    }

    @Test
    public void createEvent_test() throws IOException {
        Person host = given_host_exists();
        Event expectedEvent = when_host_creates_new_event_and_invites_guests(host);
        then_host_can_see_invitation_statistics(expectedEvent);
        tearDown();
    }

    public Person given_host_exists() {
        return personMapper.getPersonById(12L).get();
    }

    public Event when_host_creates_new_event_and_invites_guests(Person host) throws IOException {
        event = createMockEvent(host);

        List<Invitation> invitations = new ArrayList();
        IntStream.range(0, 35).forEach(i -> invitations.add(createMockInvitation(createMockPerson())));
        event.setInvitations(invitations);

        eventVariables = new ObjectMapper().createObjectNode();
        eventInput = new ObjectMapper().createObjectNode();

        eventVariables.putPOJO("host", event.getHost());
        eventVariables.putPOJO("title", event.getTitle());
        eventVariables.putPOJO("date_of_event", event.getDate_of_event().toString());
        eventVariables.putPOJO("time_of_event", event.getTime_of_event().toString());
        eventVariables.putPOJO("invitations", event.getInvitations());

        eventInput.putPOJO("eventInput", eventVariables);

        GraphQLResponse graphQLResponse = graphQLTestTemplate.perform("mutations/createEvent.graphql", eventInput);
        Response response = graphQLResponse.get("$.data.response", Response.class);
        String responseMessage = response.getMessage();

        assertEquals("Event was successfully saved", responseMessage);

        event.setId(response.getId());

        return event;
    }

    public void then_host_can_see_invitation_statistics(Event expectedEvent) throws IOException {
        variables = new ObjectMapper()
                .createObjectNode()
                .put("event_id", expectedEvent.getId());

        ObjectMapper mapper = new ObjectMapper();
        GraphQLResponse graphQLResponse = graphQLTestTemplate.perform("queries/getInvitationsByEventId.graphql", variables);

        List<LinkedHashMap> objectList = graphQLResponse.get("$.data.invitations", List.class);
        List<Invitation> actualInvitations = new ObjectMapper().convertValue(objectList, new TypeReference<>() {});
        List<Invitation> expectedInvitations = new ArrayList(event.getInvitations());

        assertInvitationListsAreEqual(expectedInvitations, actualInvitations);
    }


}