package com.himanshu.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublcController {
    @GetMapping("/health-check")
    public String healthCheck(){
        return "Okk";
    }
}
