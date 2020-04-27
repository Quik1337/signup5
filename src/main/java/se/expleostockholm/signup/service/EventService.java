package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.repository.EventMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {

    private final EventMapper eventMapper;

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
}
