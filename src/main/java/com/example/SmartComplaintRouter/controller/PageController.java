package com.example.SmartComplaintRouter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/complaint")
    public String complaintPage() {
        return "complaint";
    }
}
