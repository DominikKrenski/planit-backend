package com.dominik.backend.controller;

import com.dominik.backend.entity.PlanitUser;
import com.dominik.backend.entity.Role;
import com.dominik.backend.response.AppResponse;
import com.dominik.backend.service.PlanitUserService;
import com.dominik.backend.service.RoleService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by dominik on 07.04.17.
 */

@RestController
@RequestMapping(value = "/user")
@Api(tags = UserController.URL, description = "Endpoint for user management")
public class UserController {

    private PlanitUserService planitUserService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(PlanitUserService planitUserService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.planitUserService = planitUserService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public static final String URL = "planit-backend.com:8888/api/user";
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/register", method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse> registerUser(@Valid @RequestBody PlanitUser planitUser) {

        logger.info("ŻĄDANIE REJESTRACJI NOWEGO UŻYTKOWNIKA");
        logger.info("UŻYTKOWNIK: " + planitUser);

        Role role = roleService.getRoleByName("ROLE_STUDENT");

        planitUser.getRoles().add(role);
        planitUser.setPassword(passwordEncoder.encode(planitUser.getPassword()));

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (planitUserService.saveUser(planitUser) == null) {
            // Rozwiązanie tymczasowe, należy sprawdzić co zwraca Spring, gdy napotka problem z zapisem do bazy danych
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Wystąpił problem podczas zapisu do bazy danych");
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Poprawnie zarejestrowano użytkownika");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);

    }
}
