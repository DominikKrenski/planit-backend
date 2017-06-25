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
}
