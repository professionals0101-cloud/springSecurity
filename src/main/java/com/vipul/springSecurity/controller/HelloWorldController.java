package com.vipul.springSecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.rmi.server.LogStream.log;

@RestController
public class HelloWorldController {

    private static final Log log = LogFactory.getLog(HelloWorldController.class);

    @GetMapping("/test")
    public String sayHello(HttpServletRequest servletRequest){
        log.info("Hello");
        return "Logged In "+servletRequest.getSession().getId();
    }
}
