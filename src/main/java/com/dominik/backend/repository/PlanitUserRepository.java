package com.dominik.backend.repository;

import com.dominik.backend.entity.PlanitUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dominik on 07.04.17.
 */
public interface PlanitUserRepository extends CrudRepository<PlanitUser, Long> {

    PlanitUser findOneByLogin(String login);
}
