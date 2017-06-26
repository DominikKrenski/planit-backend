package com.dominik.backend.controller;

import com.dominik.backend.entity.Event;
import com.dominik.backend.entity.PlanitUser;
import com.dominik.backend.entity.Tag;
import com.dominik.backend.exception.CustomException;
import com.dominik.backend.response.AppResponse;
import com.dominik.backend.service.EventService;
import com.dominik.backend.service.PlanitUserService;
import com.dominik.backend.service.TagService;
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
import java.util.*;

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
    private TagService tagService;

    @Autowired
    public EventController(EventService eventService, PlanitUserService userService, TagService tagService) {
        this.eventService = eventService;
        this.userService = userService;
        this.tagService = tagService;
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

    @RequestMapping(value = "/add-tags/{id}", method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse> addTagsToEvent(@PathVariable Long id, @RequestBody Set<Long>tagsIds) {

        logger.info("NADESZŁO ŻĄDANIE DODANIA TAGÓW DO EVENTU");
        logger.info("Tag ids: " + tagsIds.toArray());

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Pobranie eventu z bazy danych
        Event event = eventService.getEventById(id);

        if (event == null) {
            response.setMessage("Brak eventu o danym id");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        String login = "";

        // Pobranie loginu aktualnego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            login = authentication.getName();

        // Pobranie użytkownika z bazy danych
        PlanitUser user = userService.findUserByLogin(login);

        // Sprawdzenie, czy user.id == event.userId; jeśli nie - wyrzucenie błędu
        if (user.getId() != event.getUser().getId()) {
            response.setMessage("Brak uprawnień do edycji eventu");
            response.setStatus(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(response, headers, HttpStatus.FORBIDDEN);
        }

        // Pobranie tagów przypisanych do danego eventu
        Set<Tag> eventTags = event.getTags();

        // Pobranie z bazy danych tagów, których id zostały przesłane w żądaniu
        Set<Tag> newTags = new HashSet<>();

        // Dodanie nowych tagów
        for (Long identifier : tagsIds) {

            Tag tag = tagService.findTagById(identifier);

            if (tag != null) {
                event.getTags().add(tag);
            }
        }

        // Zapisanie zmian do bazy danych
        if (eventService.saveEvent(event) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie dodano tagi do wydarzenia");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "remove-tags/{id}", method = RequestMethod.DELETE,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse>removeTagsFromEvent(@PathVariable Long id, @RequestBody Set<String> tagNames) {

        logger.info("NADESZŁO ŻĄDANIE USUNIĘCIA TAGA/TAGÓW Z WYDARZENIA");
        logger.info("Tag names: " + tagNames.toArray());

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Pobranie eventu z bazy danych
        Event event = eventService.getEventById(id);

        if (event == null) {
            response.setMessage("Brak eventu o danym id");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        String login = "";

        // Pobranie loginu aktualnego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            login = authentication.getName();

        // Pobranie użytkownika z bazy danych
        PlanitUser user = userService.findUserByLogin(login);

        // Sprawdzenie, czy user.id == event.userId; jeśli nie - wyrzucenie błędu
        if (user.getId() != event.getUser().getId()) {
            response.setMessage("Brak uprawnień do edycji eventu");
            response.setStatus(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(response, headers, HttpStatus.FORBIDDEN);
        }

        // Usunięcie tagów z wydarzenia
        for (String tagName : tagNames) {

            Tag tag = tagService.findTagByName(tagName);

            if (tag == null) {
                response.setMessage("Brak tagu o danej nazwie");
                response.setStatus(HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
            }

            event.getTags().remove(tag);
        }

        // Zapisanie zmian do bazy danych
        if (eventService.saveEvent(event) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie usunięto tagi z wydarzenia");
        response.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
