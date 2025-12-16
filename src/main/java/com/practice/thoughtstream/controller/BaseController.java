package com.practice.thoughtstream.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @GetMapping("/home")
    public String getHome1(){
        return "home1";
    }

    @GetMapping("/page1")
    public String getPage1(){
        return "pag1";
    }

    @GetMapping("/public")
    public String getPublicPage(){
        return "public Page";
    }
}
