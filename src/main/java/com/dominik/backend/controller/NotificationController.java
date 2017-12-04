package com.dominik.backend.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/notification")
@Api(tags = NotificationController.URL, description = "Endpoint for notifications management")
public class NotificationController {

    public static final String URL = "planit-backend.com:8888/api/notification";
}
