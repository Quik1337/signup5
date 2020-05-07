package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.UserMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService {

    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final InvitationMapper invitationMapper;
    private final InvitationEmailService invitationEmailService;

    public List<Event> getEvents() {
        return eventMapper.getEvents();
    }

    public Event getEventById(Long id) throws Exception {
        return eventMapper.getEventById(id).orElseThrow(() -> new Exception("No event found!"));
    }

    public List<Event> getEventsByHostId(Long host_id) throws Exception {

        List<Event> events = eventMapper.getEventsByHostId(host_id);
        if (events.size() == 0) {
            throw new Exception("No events found!");
        }
        return events;
    }

    public void createEvent(Event event) throws Exception {

        // Check date and time of the event

        if (event.getDate_of_event().isBefore(LocalDate.now()))
            throw new Exception("The date of the event cannot be set in the past.");

        if (event.getStart_time_of_event().equals(event.getEnd_time_of_event()))
            throw new Exception("The start and end time of the event cannot be the same.");

        if (event.getDate_of_event().equals(LocalDate.now()) && event.getStart_time_of_event().isBefore(LocalTime.now()))
            throw new Exception("The start time of the event cannot be set in the past.");

        if (event.getDate_of_event().equals(LocalDate.now()) && event.getEnd_time_of_event().isBefore(LocalTime.now()))
            throw new Exception("The end time of the event cannot be set in the past.");

        if (event.getDate_of_event().equals(LocalDate.now()) && event.getEnd_time_of_event().equals(LocalTime.now()))
            throw new Exception("The end time of the event needs to be set in the future.");

        eventMapper.createEvent(event);

        for (Invitation tempInvitation : event.getInvitations()) {

            Optional<User> optionalUser = userMapper.getUserByEmail(tempInvitation.getGuest().getEmail());

            if (optionalUser.isEmpty()) {

                userMapper.createUser(tempInvitation.getGuest());
                optionalUser = userMapper.getUserByEmail(tempInvitation.getGuest().getEmail());
            }

            optionalUser.ifPresent(tempInvitation::setGuest);

            tempInvitation.setEvent(event);

            invitationMapper.createInvitation(tempInvitation);

            invitationEmailService.sendEmail(tempInvitation);
        }

    }

}
