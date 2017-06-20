package com.dominik.backend.repository;

import com.dominik.backend.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dominik on 20.06.2017.
 */
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByIsArchiveFalse();
}
