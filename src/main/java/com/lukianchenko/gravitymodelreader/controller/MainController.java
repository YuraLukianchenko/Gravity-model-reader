package com.lukianchenko.gravitymodelreader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/greetings")
    public String greetings() {
        return "greetings";
    }

}
