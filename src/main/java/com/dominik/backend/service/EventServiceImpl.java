package com.dominik.backend.service;

import com.dominik.backend.entity.Event;
import com.dominik.backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by dominik on 20.06.2017.
 */

@Service
public class EventServiceImpl implements EventService {

    private EventRepository repository;

    @Autowired
    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event saveEvent(Event event) {
        return repository.save(event);
    }

    @Override
    public Event getEventById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<Event> getAllActiveEventsWithoutPrivates() {
        return repository.findAllByIsAcceptedTrueAndIsPrivateFalseAndIsArchiveFalse();
    }

    @Override
    public List<Event> getAllArchivedEvents() {
        return repository.findAllByIsAcceptedTrueAndIsPrivateFalseAndIsArchiveTrue();
    }

    @Override
    public List<Event> gelAllNonAcceptedEvents() {
        return repository.findAllByIsAcceptedFalseAndIsPrivateFalse();
    }

    @Override
    public List<Event> getAllPastEvents(LocalDate date) {
        return repository.findAllByStartDateBeforeAndIsArchiveFalseAndIsPrivateFalse(date);
    }

    @Override
    public List<Event> getEventsByUserId(Long id) {
        List<Event> events = repository.getEventsByUserId(id);
        return events;
    }

    @Override
    public List<Event> getAllEvents() {
        return repository.findAllByIsPrivateFalse();
    }

    @Override
    public Iterable<Event> saveEvents(List<Event> events) {
        return  repository.save(events);
    }
}
