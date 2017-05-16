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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dominik on 12.04.2017.
 */

@RestController
@RequestMapping(value = "/admin")
@Api(tags = AdminController.URL, description = "Endpoint for admin tasks")
public class AdminController {

    public static final String URL = "planit-backend.com:8888/api/admin";

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private PlanitUserService planitUserService;
    private RoleService roleService;

    @Autowired
    public AdminController(PlanitUserService planitUserService, RoleService roleService) {
        this.planitUserService = planitUserService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlanitUser> getUsers() {

        logger.info("NADESZŁO ŻĄDANIE WYPISANIA WSZYSTKICH UŻYTKOWNIKÓW APLIKACJI");

        List<PlanitUser> users = planitUserService.getAllUsers();

        return users;
    }

    @RequestMapping(value = "/user/grant/{id}", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse> grantAdminRights(@PathVariable Long id) {

        logger.info("NADESZŁO ŻĄDANIE NADANIA PRAW ADMINISTRATORA UŻYTKOWNIKOWI");

        PlanitUser user = planitUserService.findUserById(id);

        Role adminRole = roleService.getRoleByName("ROLE_ADMIN");

        user.getRoles().add(adminRole);

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        if (planitUserService.saveUser(user) == null ) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Wystąpił problem podczas zapisu do bazy danych");
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Poprawnie nadano przywileje administratora");

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
