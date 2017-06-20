package com.dominik.backend.service;

import com.dominik.backend.entity.Event;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by dominik on 20.06.2017.
 */
public interface EventService {

    Event saveEvent(Event event);
    Iterable<Event> saveEvents(List<Event> events);
    List<Event> getAllEvents();
    List<Event> getAllActiveEvents();
    List<Event> getAllArchivedEvents();
    List<Event> getAllPastEvents(LocalDate date);
    Event getEventById(Long id);
    List<Event> getEventsByUserId(Long id);
}
