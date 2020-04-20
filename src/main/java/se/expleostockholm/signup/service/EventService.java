package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.exception.EventAlreadyExistsException;
import se.expleostockholm.signup.exception.EventNotFoundException;
import se.expleostockholm.signup.exception.InvalidDateException;
import se.expleostockholm.signup.exception.UserNotFoundException;
import se.expleostockholm.signup.repository.EventMapper;

import java.util.List;

import static se.expleostockholm.signup.service.ServiceUtil.isValidDate;

@Service
@AllArgsConstructor
public class EventService {

    private final EventMapper eventMapper;
    private final InvitationService invitationService;
    private final UserService userService;

    /*
    public Event createEvent(Event event) {
        if (!isDuplicateEvent(event)) {
            if (!userService.doesUserExist(event.getHost())) {
                if (isValidDate(event.getDate_of_event())) {
                    eventMapper.createEvent(event);
                    invitationService.saveInvitations(event.getInvitations(), event.getId());
                    return event;
                }
                throw new InvalidDateException("Invalid date. Start of event cannot be in the past!");
            }
            throw new UserNotFoundException("Event host not found!");
        }
        throw new EventAlreadyExistsException("'" + event.getTitle() + "': " + event.getDate_of_event() + ": " + event.getTime_of_event() + " - Event already exists");
    }
    */

    public void createEvent(Event event) {

        eventMapper.createEvent(event);

        System.out.println("event ID: " + event.getId());
    }

    public List<Event> getAllEvents() {
        List<Event> events = eventMapper.getAllEvents();
        if (events.size() == 0) {
            throw new EventNotFoundException("No events found!");
        }
        return events;
    }

    public Event getEventById(Long id) {
        return eventMapper.getEventById(id).orElseThrow(() -> new EventNotFoundException("No event found!"));
    }

    public boolean isDuplicateEvent(Event event) {
        return eventMapper.isDuplicateEvent(event) == 1;
    }

    public List<Event> getEventsByHostId(Long id) {

        List<Event> events = eventMapper.getEventsByHostId(id);
        if (events.size() == 0) {
            throw new EventNotFoundException("No events found!");
        }
        return events;
    }
}
