package com.example.trading.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "api/users")
public class HomeController {

    @GetMapping(value = "/v")
    public String home(){
        return "Welcome to trading platform";
    }

}
