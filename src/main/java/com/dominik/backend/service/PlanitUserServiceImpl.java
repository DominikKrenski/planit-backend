package com.dominik.backend.service;

import com.dominik.backend.entity.PlanitUser;
import com.dominik.backend.repository.PlanitUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<PlanitUser> getAllUsers() {
        return (List<PlanitUser>) userRepository.findAll();
    }

    @Override
    public PlanitUser findUserById(Long id) {
        return userRepository.findOne(id);
    }
}
