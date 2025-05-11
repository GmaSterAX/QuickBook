package com.softwarearchitecture.QuickBook.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;


@Controller
public class PageController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // templates/register.html
    }
}
