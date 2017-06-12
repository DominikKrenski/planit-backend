package com.dominik.backend.controller;

import com.dominik.backend.entity.Tag;
import com.dominik.backend.response.AppResponse;
import com.dominik.backend.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by dominik on 12.06.17.
 */

@RestController
@RequestMapping(value = "/tag")
public class TagController {

    public static final String URL = "planit-backend.com:8888/api/tag";

    private Logger logger = LoggerFactory.getLogger(TagController.class);

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
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
}
