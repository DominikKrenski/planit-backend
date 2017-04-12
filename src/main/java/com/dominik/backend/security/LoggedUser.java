package com.dominik.backend.security;

import com.dominik.backend.entity.PlanitUser;
import com.dominik.backend.entity.Role;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

/**
 * Created by dominik on 12.04.2017.
 *
 * Spring Security wymaga interfejsu UserDetails; w celu uniknięcia implementowania wszystkich metod,
 * wykorzystana zostanie klasa org.springframework.security.core.userdetails.User
 */

public class LoggedUser extends User {

    private PlanitUser planitUser;

    public LoggedUser(PlanitUser planitUser) {
        super(planitUser.getLogin(), planitUser.getPassword(), AuthorityUtils.createAuthorityList(planitUser.getRoles().toString()));
    }

    public PlanitUser getPlanitUser() {
        return planitUser;
    }

    public Set<Role> getRoles() {
        return planitUser.getRoles();
    }
}
