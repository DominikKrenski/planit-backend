package com.dominik.backend.controller;

import com.dominik.backend.entity.Event;
import com.dominik.backend.entity.PlanitUser;
import com.dominik.backend.response.AppResponse;
import com.dominik.backend.service.EventService;
import com.dominik.backend.service.PlanitUserService;
import com.dominik.backend.util.EventResponse;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dominik on 20.06.2017.
 */

@RestController
@RequestMapping(value = "/event")
@Api(tags = EventController.URL, description = "Endpoint for event management")
public class EventController {

    public static final String URL = "planit-backend.com:8888/api/event";

    private Logger logger = LoggerFactory.getLogger(EventController.class);

    private EventService eventService;
    private PlanitUserService userService;

    @Autowired
    public EventController(EventService eventService, PlanitUserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse> createEvent(@Valid @RequestBody Event event) {

        logger.info("NADESZŁO ŻĄDANIE UTWORZENIA NOWEGO EVENTU");
        logger.info("Event: " + event);

        String login = "";

        // Pobranie nazwy aktualnie zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            login = authentication.getName();

        PlanitUser user = userService.findUserByLogin(login);

        // Przypisanie użytkownika do eventu
        event.setUser(user);

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Zapisanie eventu do bazy
        if (eventService.saveEvent(event) == null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Błąd podczas zapisu do bazy danych");
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie zapisano event");
        response.setStatus(HttpStatus.CREATED);

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }



}
