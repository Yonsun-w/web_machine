package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apiController")
public class apiController {

    @GetMapping("/expertApi")
    public String expertApi(){

        return "expertApi";
    }
    @GetMapping("/bookApi")
    public String bookApi(){

        return "bookApi";
    }
    @GetMapping("/projectApi")
    public String projectApi(){

        return "projectApi";
    }
    @GetMapping("/loginApi")
    public String loginApi(){

        return "loginApi";
    }
}
