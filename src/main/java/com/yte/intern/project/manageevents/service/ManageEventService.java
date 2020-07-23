package com.yte.intern.project.manageevents.service;

import com.yte.intern.project.manageevents.entity.Event;
import com.yte.intern.project.manageevents.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageEventService {

    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public Event findByEventKey(String eventKey) {
        return eventRepository.findByEventKey(eventKey);
    }

    public void deleteByEventKey(String eventKey) { eventRepository.deleteByEventKey(eventKey);
    }
}
