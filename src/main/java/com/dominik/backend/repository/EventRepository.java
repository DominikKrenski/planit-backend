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

    List<Event> findAllByIsAcceptedTrueAndIsPrivateFalseAndIsArchiveFalse();
    List<Event> findAllByIsAcceptedTrueAndIsPrivateFalseAndIsArchiveTrue();
    List<Event> findAllByIsAcceptedFalseAndIsPrivateFalse();
    List<Event> findAllByStartDateBeforeAndIsArchiveFalseAndIsPrivateFalse(LocalDate date);
    List<Event> findAllByIsPrivateFalse();

    @Query(value = "SELECT * FROM events WHERE user_id = ?1", nativeQuery = true)
    List<Event> getEventsByUserId(Long id);
}
