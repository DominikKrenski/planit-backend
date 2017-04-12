package com.dominik.backend.security;

import com.dominik.backend.entity.PlanitUser;
import com.dominik.backend.service.PlanitUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by dominik on 12.04.2017.
 *
 * Klasa wykorzystywana przez AuthenticationManager do przechowywania informacji o obecnie zalogowanym użytkowniku
 */

@Service
public class LoggedUserDetailsService implements UserDetailsService {

    private PlanitUserService planitUserService;

    @Autowired
    public LoggedUserDetailsService(PlanitUserService planitUserService) {
        this.planitUserService = planitUserService;
    }

    @Override
    public LoggedUser loadUserByUsername(String login) throws UsernameNotFoundException {

        PlanitUser planitUser = planitUserService.findUserByLogin(login);

        if (planitUser == null) {
            throw new UsernameNotFoundException("Użytkownik " + login + " nie istnieje");
        }

        return new LoggedUser(planitUser);
    }
}
