package com.dominik.backend.controller;

import com.dominik.backend.entity.Event;
import com.dominik.backend.entity.PlanitUser;
import com.dominik.backend.entity.Role;
import com.dominik.backend.entity.Tag;
import com.dominik.backend.exception.CustomException;
import com.dominik.backend.response.AppResponse;
import com.dominik.backend.service.EventService;
import com.dominik.backend.service.PlanitUserService;
import com.dominik.backend.service.TagService;
import com.dominik.backend.util.EventResponse;
import com.dominik.backend.util.UpdateEvent;
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

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse> updateEvent(@PathVariable Long id, @Valid @RequestBody UpdateEvent updateEvent) {

        logger.info("NADESZŁO ŻĄDANIE ZAKTUALIZOWANIE EVENTU O ID = " + id);

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Event event = eventService.getEventById(id);

        if (event == null) {
            response.setMessage("Brak eventu o danym id");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        // Pobranie loginu aktualnego użytkownika
        String login = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            login = authentication.getName();

        // Pobranie użytkownika z bazy danych
        PlanitUser user = userService.findUserByLogin(login);

        // Sprawdzenie, czy user.Id = event.userId, jeśli nie - wyrzucenie błędu
        if (user.getId() != event.getUser().getId()) {
            response.setMessage("Brak uprawnień do edycji eventu");
            response.setStatus(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(response, headers, HttpStatus.FORBIDDEN);
        }

        // Przypisanie nowych wartości do eventu
        event.setName(updateEvent.getName());
        event.setPlace(updateEvent.getPlace());
        event.setType(updateEvent.getType());
        event.setStartDate(updateEvent.getStartDate());
        event.setStartHour(updateEvent.getStartHour());
        event.setEndHour(updateEvent.getEndHour());
        event.setIsImportant(updateEvent.getIsImportant());
        event.setIsPrivate(updateEvent.getIsPrivate());

        // Zapisanie eventu do bazy danych
        if (eventService.saveEvent(event) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie zaktualizowano event");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
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

    @RequestMapping(value = "remove-tags/{id}", method = RequestMethod.PUT,
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

    @RequestMapping(value = "/set-private/{id}", method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse> setPrivate(@PathVariable Long id) {

        logger.info("NADESZŁO ŻĄDANIE USTAWIENIA FLAGI IS_PRIVATE = TRUE");

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

        // Pobranie loginu aktualnego użytkownika
        String login = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            login = authentication.getName();

        // Pobranie użytkownika z bazy danych
        PlanitUser user = userService.findUserByLogin(login);

        // Sprawdzenie, czy user.id = event.userId; jeśli nie - wyrzucenie błędu
        if (user.getId() != event.getUser().getId()) {
            response.setMessage("Brak uprawnień do edycji eventu");
            response.setStatus(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(response, headers, HttpStatus.FORBIDDEN);
        }

        event.setIsPrivate(true);

        // Zapisanie zmian do bazy
        if (eventService.saveEvent(event) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie zaktualizowano wpis");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/revoke-private/{id}", method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse> revokePrivate(@PathVariable Long id) {

        logger.info("NADESZŁO ŻĄDANIE USTAWIENIA FLAGI IS_PRIVATE = FALSE");

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

        // Sprawdzenie, czy user.id = even.userId, jeśli nie - wyrzucenie błędu
        if (user.getId() != event.getUser().getId()) {
            response.setMessage("Brak uprawnień do edycji eventu");
            response.setStatus(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(response, headers, HttpStatus.FORBIDDEN);
        }

        event.setIsPrivate(false);

        // Zapisanie zaktualizowanego eventu do bazy danych
        if (eventService.saveEvent(event) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie zaktualizowano event");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/set-important/{id}", method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse> setImportant(@PathVariable Long id) {

        logger.info("NADESZŁO ŻĄDANIE USTAWIENIA FLAGI IS_IMPORTANT = TRUE");

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

        // Pobranie loginu aktualnego użytkownika
        String login = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            login = authentication.getName();

        // Pobranie użytkownika z bazy danych
        PlanitUser user = userService.findUserByLogin(login);

        // Sprawdzenie, czy user.id = event.userId, jeśli nie - wyrzucenie błędu
        if (user.getId() != event.getUser().getId()) {
            response.setMessage("Brak uprawnień do edycji eventu");
            response.setStatus(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(response, headers, HttpStatus.FORBIDDEN);
        }

        event.setIsImportant(true);

        // Zapisanie zmian do bazy danych
        if (eventService.saveEvent(event) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie zaktualizowano wpis");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/revoke-important/{id}", method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse> revokeImportant(@PathVariable Long id) {

        logger.info("NADESZŁO ŻĄDANIE USTAWIENIA FLAGI IS_IMPORTANT = FALSE");

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Event event = eventService.getEventById(id);

        if (event == null) {
            response.setMessage("Brak eventu o danym id");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        // Pobranie loginu aktualnego użytkownika
        String login = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            login = authentication.getName();

        // Pobranie użytkownika za bazy danych
        PlanitUser user = userService.findUserByLogin(login);

        // Sprawdzenie, czy user.id = event.userId, jeśli nie - wyrzucenie błędu
        if (user.getId() != event.getUser().getId()) {
            response.setMessage("Brak uprawnień do edycji eventu");
            response.setStatus(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(response, headers, HttpStatus.FORBIDDEN);
        }

        event.setIsImportant(false);

        // Zapisanie zmian do bazy danych
        if (eventService.saveEvent(event) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie zaktualizowano wpis");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-privates", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAllPrivateEvents() {

        logger.info("NADESZŁO ŻĄDANIE ZWRÓCENIA PRYWATNYCH EVENTÓW AKTUALNIE ZALOGOWANEGO UŻYTKOWNIKA");

        String login = "";

        // Pobranie loginu aktualnego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            login = authentication.getName();

        PlanitUser user = userService.findUserByLogin(login);
        Long id = user.getId();

        List<Event> events = eventService.getEventsByUserId(id);
        List<Event> privateEvents = new LinkedList<>();

        for (Event event : events) {
            if (event.getIsPrivate() == true)
                privateEvents.add(event);
        }

        return privateEvents;
    }

    @RequestMapping(value = "/get-important", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAllImportantEvents() {

        logger.info("NADESZŁO ŻĄDANIE ZWRÓCENIE WAŻNYCH DLA AKTUALNIE ZALOGOWANEGO UŻYTKOWNIKA EVENTÓW");

        String login = "";

        // Pobranie loginu aktualnego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            login = authentication.getName();

        PlanitUser user = userService.findUserByLogin(login);

        Long id = user.getId();

        List<Event> events = eventService.getEventsByUserId(id);
        List<Event> importantEvents = new LinkedList<>();

        for (Event event : events) {
            if (event.getIsImportant() == true)
                importantEvents.add(event);
        }

        return importantEvents;

    }

    @RequestMapping(value = "/active", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getActiveEvents() {

        logger.info("NADESZŁO ŻĄDANIE ZWRÓCENIE LISTY AKTYWNYCH EVENTÓW (BEZ PRYWATNYCH)");

        //List<Event> events = eventService.getAllActiveEventsWithoutPrivates();
        List<Event> events = eventService.getAllActiveEvents();

        return events;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Event getEventById(@PathVariable Long id) {

        logger.info("NADESZŁO ŻĄDANIE ZWRÓCENIA EVENTU ZA POMOCĄ ID");

        Event event = eventService.getEventById(id);

        return event;
    }

    @RequestMapping(value = "/archive", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getArchivedEvents() {

        logger.info("NADESZŁO ŻĄDANIE ZWRÓCENIA ARCHIWALNYCH EVENTÓW");

        List<Event> events = eventService.getAllArchivedEvents();

        return events;
    }

    @RequestMapping(value = "/not-accepted", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('ADMIN')")
    public List<Event> getNotAcceptedEvents() {

        logger.info("NADESZŁO ŻĄDANIE ZWRÓCENIA EVENTÓW, KTÓRE NIE ZOSTAŁY JESZCZE ZAAKCEPTOWANE");

        String login = "";

        // Pobranie nazwy aktualnie zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof  AnonymousAuthenticationToken))
            login = authentication.getName();

        PlanitUser user = userService.findUserByLogin(login);

        Set<Role> rolesSet = user.getRoles();

        Set<String> roles = new HashSet<>();

        for (Role role : rolesSet)
            roles.add(role.getName());

        List<Event> events;

        if (roles.contains("ROLE_ADMIN"))
            events = eventService.gelAllNonAcceptedEvents();
        else
            events = eventService.getUserNotAcceptedEvents(user.getId());

        return events;
    }

    @RequestMapping(value = "/set-accepted/{id}", method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> setAccepted(@PathVariable Long id) {

        logger.info("NADESZŁO ŻĄDANIE USTAWIENIA FLAGI IS_ACCEPTED NA TRUE");

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Event event = eventService.getEventById(id);

        if (event == null) {
            response.setMessage("Event o danym id nie istnieje");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        event.setIsAccepted(true);

        if (eventService.saveEvent(event) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie zaktualizowano wpis");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/revoke-accepted/{id}", method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> revokeAccepted(@PathVariable Long id) {

        logger.info("NADESZŁO ŻĄDANIE USTAWIENIA FLAGI IS_ACCEPTED NA FALSE");

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Event event = eventService.getEventById(id);

        if (event == null) {
            response.setMessage("Event o danym id nie istnieje");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        event.setIsAccepted(false);

        if (eventService.saveEvent(event) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie zaktualizowano wpis");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/past", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('ADMIN')")
    public List<EventResponse> getAllPastEvents() {

        logger.info("NADESZŁO ŻĄDANIE ZWRÓCENIA EVENTÓW, KTÓRE SĄ JUŻ NIEAKTUALNE, ALE POLE IS_ARCHIVE = FALSE");

        LocalDate date = LocalDate.now();

        String login = "";

        // Pobranie nazwy aktualnie zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            login = authentication.getName();

        PlanitUser user = userService.findUserByLogin(login);

        Set<Role> roleSet = user.getRoles();

        Set<String> roles = new HashSet<>();

        for (Role role : roleSet)
            roles.add(role.getName());

        List<Event> events;

        if (roles.contains("ROLE_ADMIN"))
            events = eventService.getAllPastEvents(date);
        else
            events = eventService.getUserPastEvents(user.getId(), date);

        List<EventResponse> eventResponses = new LinkedList<>();

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

    @RequestMapping(value = "/set-archive/{id}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> setArchive(@PathVariable Long id) {

        logger.info("NADESZŁO ŻĄDANIE USTAWIENIA FLAGI IS_ARCHIVE NA TRUE");

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Event event = eventService.getEventById(id);

        if (event == null) {
            response.setMessage("Event o danym id nie istnieje");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        event.setIsArchive(true);

        if (eventService.saveEvent(event) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie zaktualizowano wpis");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
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


    @RequestMapping(value="/date", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventResponse> getEventsByDateRange(@RequestParam("start") String startDate, @RequestParam("end") String endDate) {

        logger.info("ŻĄDANIE ZWRÓCENIA LISTY EVENTÓW ODBYWAJĄCYCH SIĘ MIĘDZY " + startDate + " A " + endDate);

        startDate = startDate.replaceAll("\\.", "/");
        endDate = endDate.replaceAll("\\.", "/");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate start =  LocalDate.parse(startDate, dateFormatter);
        LocalDate stop = LocalDate.parse(endDate, dateFormatter);

        List<Event> events = eventService.getEventsInRange(start, stop);
        List<EventResponse> eventResponses = new ArrayList<>();

        for (Event event : events) {
            EventResponse eventResponse = new EventResponse();
            eventResponse.setId(event.getId());
            eventResponse.setName(event.getName());
            eventResponse.setPlace(event.getPlace());
            eventResponse.setType(event.getType());
            eventResponse.setStartDate(event.getStartDate());
            eventResponse.setStartHour(event.getStartHour());
            eventResponse.setEndHour(event.getEndHour());
            eventResponse.setIsArchive(event.getIsArchive());
            eventResponse.setIsAccepted(event.getIsAccepted());
            eventResponse.setUserId(event.getUser().getId());

            eventResponses.add(eventResponse);
        }

        return eventResponses;
    }

    @RequestMapping(value="/by-tags", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getEventsByTags(@RequestParam("ids") String tags) {

        logger.info("ŻĄDANIE ZWRÓCENIA LISTY EVENTÓW ZAWIERAJĄCYCH TAGI O IDENTYFIKATORACH " + tags);

        List<String> idsString = Arrays.asList(tags.split(","));
        List<Long> ids = new ArrayList<>();

        for (String id : idsString) {
            ids.add(Long.parseLong(id));
        }

        List<Event> events = eventService.getEventsByTagIds(ids);

        return events;
    }
}
