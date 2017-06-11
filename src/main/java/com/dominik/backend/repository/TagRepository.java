package com.dominik.backend.repository;

import com.dominik.backend.entity.Tag;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dominik on 11.06.2017.
 */
public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findOneByName(String name);
}
