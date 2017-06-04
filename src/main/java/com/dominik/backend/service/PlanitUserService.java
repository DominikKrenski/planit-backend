package com.dominik.backend.service;

import com.dominik.backend.entity.PlanitUser;

import java.util.List;

/**
 * Created by dominik on 07.04.17.
 */
public interface PlanitUserService {

    PlanitUser saveUser(PlanitUser user);
    PlanitUser findUserByLogin(String login);
    PlanitUser findUserById(Long id);
    List<PlanitUser> getAllUsers();
    void deleteUserById(Long id);
}
