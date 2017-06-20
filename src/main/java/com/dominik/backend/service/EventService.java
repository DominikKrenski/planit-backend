package com.dominik.backend.service;

import com.dominik.backend.entity.Event;

import java.util.List;

/**
 * Created by dominik on 20.06.2017.
 */
public interface EventService {

    Event saveEvent(Event event);
    List<Event> getAllEvents();
    List<Event> getAllActiveEvents();
}
