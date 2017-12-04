package com.dominik.backend.controller;

import com.dominik.backend.entity.Notification;
import com.dominik.backend.response.AppResponse;
import com.dominik.backend.service.NotificationService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/notification")
@Api(tags = NotificationController.URL, description = "Endpoint for notifications management")
public class NotificationController {

    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public static final String URL = "planit-backend.com:8888/api/notification";
    private final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @RequestMapping(value = "/create", method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> saveNotification(@Valid @RequestBody Notification notification) {

        logger.info("ŻĄDANIE DODANIA NOWEGO POWIADOMIENIA");
        logger.info("Powiadomienie: " + notification);

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (notificationService.saveNotification(notification) == null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Wystąpił problem podczas zapisu do bazy danych");
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Poprawnie zapisano powiadomienie");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> deleteNotificationById(@RequestParam("id") Long id) {

        logger.info("ŻĄDANIE USUNIĘCIA POWIADOMIENIA O DANYM ID");
        logger.info("ID: " + id);

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();

        notificationService.deleteNotificationById(id);

        headers.setContentType(MediaType.APPLICATION_JSON);
        response.setMessage("Poprawnie usunięto powiadomianie");
        response.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Notification> getNotifications() {

        logger.info("ŻĄDANIE ZWRÓCENIA WSZYSTKICH POWIADOMIEŃ");

        List<Notification> notifications = notificationService.getAllNotifications();

        return notifications;
    }

    @RequestMapping(value = "/get-by-id", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Notification getNotificationById(@RequestParam("id") Long id) {

        logger.info("POBRANIE OGŁOSZENIA O ID: " + id);

        Notification notification = notificationService.getNotificationById(id);

        return notification;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> updateNotification(@Valid @RequestBody Notification notification) {

        logger.info("ŻĄDANIE AKTUALIZACJI OGŁOSZENIA");

        Notification notify = notificationService.getNotificationById(notification.getId());

        notify.setTitle(notification.getTitle());
        notify.setNotification(notification.getNotification());

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (notificationService.saveNotification(notify) == null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Wystąpił błąd podczas zapisu do bazy danych");
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setStatus(HttpStatus.OK);
        response.setMessage("Poprawnie zaktualizowano ogłoszenie");

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
