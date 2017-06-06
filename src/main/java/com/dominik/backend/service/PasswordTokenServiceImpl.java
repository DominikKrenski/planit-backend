package com.dominik.backend.service;

import com.dominik.backend.entity.PasswordToken;
import com.dominik.backend.repository.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dominik on 05.06.2017.
 */

@Service
public class PasswordTokenServiceImpl implements PasswordTokenService {

    private PasswordTokenRepository tokenRepository;

    @Autowired
    public PasswordTokenServiceImpl(PasswordTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public PasswordToken saveToken(PasswordToken token) {
        return tokenRepository.save(token);
    }

    @Override
    public PasswordToken findToken(String token) {
        return tokenRepository.findOneByToken(token);
    }

    @Override
    public void deleteToken(PasswordToken token) {
        tokenRepository.delete(token);
    }
}
