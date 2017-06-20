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
    public List<Event> getAllEvents() {
        List<Event> events = (List<Event>) repository.findAll();
        return events;
    }

    /**
     * Metoda zwracająca wszystkie wydarzenia, których pole isArchive = false
     */
    @Override
    public List<Event> getAllActiveEvents() {
        List<Event> events = repository.findByIsArchiveFalse();
        return events;
    }

    /**
     * Metoda zwracająca wszystkie wydarzenia, których pole isArchive = true
     */
    @Override
    public List<Event> getAllArchivedEvents() {
        List<Event> events = repository.findByIsArchiveTrue();
        return events;
    }

    /**
     * Metoda zwracająca wszystkie wydarzenia, które są wcześniejsze od bieżącej daty oraz mają pole isArchive = false
     */
    @Override
    public List<Event> getAllPastEvents(LocalDate date) {
        List<Event> events = repository.findByStartDateBeforeAndIsArchiveFalse(date);
        return events;
    }

    @Override
    public Event getEventById(Long id) {
        Event event = repository.findOne(id);
        return event;
    }
}
