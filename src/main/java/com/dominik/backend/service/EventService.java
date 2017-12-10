package com.dominik.backend.service;

import com.dominik.backend.entity.Event;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by dominik on 20.06.2017.
 */
public interface EventService {

    Event saveEvent(Event event);
    Event getEventById(Long id);
    List<Event> getAllActiveEventsWithoutPrivates();
    List<Event> getAllArchivedEvents();
    List<Event> gelAllNonAcceptedEvents();
    List<Event> getAllPastEvents(LocalDate date);
    List<Event> getEventsByUserId(Long id);
    List<Event> getEventsByTagName(String tagName);
    List<Event> getAllEvents();
    List<Event> getWholeEventsList();
    Iterable<Event> saveEvents(List<Event> events);
    List<Event> getAllActiveEvents(LocalDate date);
    List<Event> getEventsInRange(LocalDate startDate, LocalDate endDate);
    List<Event> getEventsByTagIds(List<Long> ids);
    List<Event> getUserNotAcceptedEvents(Long id);
    List<Event> getUserPastEvents(LocalDate date);
}
