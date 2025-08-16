package com.vipul.springSecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/test")
    public String sayHello(HttpServletRequest servletRequest){
        return "Logged In "+servletRequest.getSession().getId();
    }
}
