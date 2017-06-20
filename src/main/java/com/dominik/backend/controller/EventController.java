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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public ResponseEntity<AppResponse> createTag(@Valid @RequestBody Event event) {

        logger.info("NADESZŁO ŻĄDANIE UTWORZENIA NOWEGO EVENTU");
        logger.info("EVENT: " + event);

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

    @RequestMapping(value = "/active", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getActiveEvents() {

        logger.info("NADZESZŁO ŻĄDANIE ZWRÓCENIA LISTY AKTYWNYCH EVENTÓW");

        List<Event> events = eventService.getAllActiveEvents();

        return  events;
    }

    @RequestMapping(value = "/archive", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getArchivedEvents() {

        logger.info("NADESZŁO ŻĄDANIE ZWRÓCENIA ARCHIWALNYCH EVENTÓW");

        List<Event> events = eventService.getAllArchivedEvents();

        return events;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventResponse> getAllEvents() {

        logger.info("NADESZŁO ŻĄDANIE ZWRÓCENIA WSZYSTKICH EVENTÓW PRZEZ ADMINA");

        List<Event> events = eventService.getAllEvents();
        List<EventResponse> eventResponses = new ArrayList<>();

        for (Event event: events) {
            EventResponse eventResponse = new EventResponse();
            eventResponse.setId(event.getId());
            eventResponse.setName(event.getName());
            eventResponse.setPlace(event.getPlace());
            eventResponse.setType(event.getType());
            eventResponse.setStartDate(event.getStartDate());
            eventResponse.setStartHour(event.getStartHour());
            eventResponse.setEndHour(event.getEndHour());
            eventResponse.setIsArchive(event.getIsArchive());
            eventResponse.setUserId(event.getUser().getId());

            eventResponses.add(eventResponse);
        }

        return eventResponses;
    }


}
