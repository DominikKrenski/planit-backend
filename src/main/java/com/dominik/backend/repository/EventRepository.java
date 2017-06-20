package com.dominik.backend.repository;

import com.dominik.backend.entity.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by dominik on 20.06.2017.
 */
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByIsArchiveFalse();
    List<Event> findByIsArchiveTrue();
    List<Event> findByStartDateBeforeAndIsArchiveFalse(LocalDate date);
    Event findOne(Long id);
}
