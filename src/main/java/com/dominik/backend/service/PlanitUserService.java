package com.dominik.backend.service;

import com.dominik.backend.entity.PlanitUser;

/**
 * Created by dominik on 07.04.17.
 */
public interface PlanitUserService {

    PlanitUser saveUser(PlanitUser user);
    PlanitUser findUserByLogin(String login);
}
