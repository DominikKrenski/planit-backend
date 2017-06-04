package com.dominik.backend.controller;

import com.dominik.backend.entity.PlanitUser;
import com.dominik.backend.entity.Role;
import com.dominik.backend.response.AppResponse;
import com.dominik.backend.service.PlanitUserService;
import com.dominik.backend.service.RoleService;
import com.dominik.backend.util.Email;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private JavaMailSender javaMailSender;

    @Autowired
    public AdminController(PlanitUserService planitUserService, RoleService roleService, JavaMailSender javaMailSender) {
        this.planitUserService = planitUserService;
        this.roleService = roleService;
        this.javaMailSender = javaMailSender;
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

    @RequestMapping(value="/user/send-email", method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendEmail(@Valid @RequestBody Email email) {

        logger.info("ŻĄDANIE WYSŁANIA WIADOMOŚCI EMAIL");
        logger.info("ADRES EMAIL ODBIORCY: " + email.getReceiverEmailAddress());

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email.getReceiverEmailAddress());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setFrom("admin@planit.com");
        mailMessage.setText(email.getMessage());

        javaMailSender.send(mailMessage);
    }
}
