package com.dominik.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dominik on 12.04.2017.
 */

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "Pobrano dane z zastrzeżonego zasobu";
    }
}
