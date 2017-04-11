package com.dominik.backend.service;

import com.dominik.backend.entity.PlanitUser;
import com.dominik.backend.repository.PlanitUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dominik on 07.04.17.
 */

@Service
public class PlanitUserServiceImpl implements PlanitUserService {

    private PlanitUserRepository userRepository;

    @Autowired
    public PlanitUserServiceImpl(PlanitUserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public PlanitUser saveUser(PlanitUser user) {
        return userRepository.save(user);
    }

    @Override
    public PlanitUser findUserByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }
}
