package com.dominik.backend.controller;

import com.dominik.backend.entity.Tag;
import com.dominik.backend.exception.CustomException;
import com.dominik.backend.response.AppResponse;
import com.dominik.backend.service.TagService;
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

/**
 * Created by dominik on 12.06.17.
 */

@RestController
@RequestMapping(value = "/tag")
@Api(tags = TagController.URL, description = "Endpoint for tag management")
public class TagController {

    public static final String URL = "planit-backend.com:8888/api/tag";

    private Logger logger = LoggerFactory.getLogger(TagController.class);

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tag> getTags() {

        logger.info("ŻĄDANIE ZWRÓCENIA WSZYSTKICH TAGÓW");

        List<Tag> tags = tagService.getAllTags();

        return tags;
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> deleteTags() {

        logger.info("ŻĄDANIE USUNIĘCIA WSZYSTKICH TAGÓW");

        tagService.deleteTags();

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        response.setMessage("Poprawnie usunięto tagi");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{tagName}", method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> deleteTagByName(@PathVariable String tagName) {

        logger.info("ŻĄDANIE USUNIĘCIA TAGA: " + tagName);

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();

        Tag tag = tagService.findTagByName(tagName);

        if (tag == null) {
            response.setMessage("Brak żądanego taga");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        tagService.deleteTag(tag);

        response.setMessage("Poprawnie usunięto tag");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse> createTag(@Valid @RequestBody Tag tag) {

        logger.info("ŻĄDANIE UTWORZENIA NOWEGO TAGA");
        logger.info("Tag: " + tag);

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (tagService.saveTag(tag) == null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Błąd podczas zapisu do bazy danych");
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Poprawnie zapisano tag");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/accept/{tagName}", method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> acceptTag(@PathVariable String tagName) {

        logger.info("ŻĄDANIE AKCEPTACJI NOWEGO TAGA PRZEZ ADMINA");
        logger.info("TAG NAME: " + tagName);

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Tag tag = tagService.findTagByName(tagName);

        if (tag == null) {
            response.setMessage("Tag: " + tagName + "nie został odnaleziony");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
        }

        tag.setIsAccepted(true);

        if (tagService.saveTag(tag) == null) {
            response.setMessage("Wystąpił problem podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Tag został zaakceptowany");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/reject/{tagName}", method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> rejectTag(@PathVariable String tagName) {

        logger.info("ŻĄDANIE COFNIĘCIA AKCEPTACJI TAGA PRZEZ ADMINA");
        logger.info("TAG NAME: " + tagName);

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Tag tag = tagService.findTagByName(tagName);

        if (tag == null)
            throw new CustomException("Tag o danej nazwie nie został znaleziony");

        tag.setIsAccepted(false);

        if (tagService.saveTag(tag) == null) {
            response.setMessage("Wystąpił problem podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Tag został oznaczony jako niezaakceptowany");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit/{tagName}", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public Tag editTag(@PathVariable String tagName) {

        logger.info("ŻĄDANIE ZWRÓCENIA TAGA PRZEZ ADMINA");
        logger.info("TAG NAME: " + tagName);

        Tag tag = tagService.findTagByName(tagName);

        if (tag == null)
            throw new CustomException("Tag o podanej nazwie nie istnieje");

        return tag;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse> editTag(@Valid @RequestBody Tag tag, @PathVariable Long id) {

        logger.info("ŻĄDANIE ZAKTUALIZOWANIA TAGA");
        logger.info("TAG: " + tag);

        AppResponse response = new AppResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Tag currentTag = tagService.findTagById(id);

        if (currentTag == null)
            throw new CustomException("Tag o danym id nie został odnaleziony");

        currentTag.setName(tag.getName());
        currentTag.setIsAccepted(tag.getIsAccepted());

        if (tagService.saveTag(currentTag) == null) {
            response.setMessage("Błąd podczas zapisu do bazy danych");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMessage("Poprawnie zaktualizowano taga");
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
