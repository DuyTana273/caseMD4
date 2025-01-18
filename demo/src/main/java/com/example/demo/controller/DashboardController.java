package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @GetMapping
    public String dashboard() {
        return "/dashboard/dashboard";
    }

    @GetMapping("/users")
    public String users() {
        return "dashboard/users/list";
    }
}
