package com.dominik.backend.repository;

import com.dominik.backend.entity.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dominik on 20.06.2017.
 */
public interface EventRepository extends CrudRepository<Event, Long> {
}
