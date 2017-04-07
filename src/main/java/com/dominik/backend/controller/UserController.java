package com.dominik.backend.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dominik on 07.04.17.
 */

@RestController
@RequestMapping(value = "/user")
@Api(tags = UserController.URL, description = "Endpoint for user management")
public class UserController {

    public static final String URL = "planit-backend.com:8080/api";
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    
}
