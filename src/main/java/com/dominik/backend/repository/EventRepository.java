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

    List<Event> findAllByIsAcceptedTrueAndIsArchiveFalseAndStartDateAfter(LocalDate date);

    List<Event> findAllByIsAcceptedTrueAndIsArchiveTrue();
    List<Event> findAllByIsAcceptedFalse();
    List<Event> findAllByStartDateBeforeAndIsArchiveFalseAndIsPrivateFalse(LocalDate date);

    //Pobranie przeszłych wydarzeń przez zwykłego użytkownika
    List<Event> findAllByStartDateBeforeAndIsArchiveFalse(LocalDate date);
    //List<Event> findAllByUserIdAndStartDateBeforeAndIsArchiveFalse(Long id, LocalDate date);

    //Pobranie niezaakceptowanych wydarzeń przez zwykłego użytkownika
    List<Event> findAllByUserIdAndIsAcceptedFalse(Long id);

    List<Event> findAllByIsPrivateFalse();

    @Query(value = "SELECT * FROM events WHERE user_id = ?1", nativeQuery = true)
    List<Event> getEventsByUserId(Long id);

    @Query(value = "SELECT * FROM events e " +
            "JOIN events_tags et ON e.id = et.event_id " +
            "JOIN tags t ON et.tag_id = t.id " +
            "WHERE t.name = ?1", nativeQuery = true)

    List<Event> getEventsByTagName(String tagName);

    List<Event> findAllByStartDateAfterAndStartDateBefore(LocalDate startDate, LocalDate endDate);

    List<Event> findAllDistinctByTagsIdIn(List<Long> ids);
}
