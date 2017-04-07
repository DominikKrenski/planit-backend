package com.dominik.backend.repository;

import com.dominik.backend.entity.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dominik on 07.04.17.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
