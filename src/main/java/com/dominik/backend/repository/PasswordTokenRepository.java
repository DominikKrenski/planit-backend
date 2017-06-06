package com.dominik.backend.repository;

import com.dominik.backend.entity.PasswordToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dominik on 05.06.2017.
 */
public interface PasswordTokenRepository extends CrudRepository<PasswordToken, Long> {

    PasswordToken findOneByToken(String token);
}
