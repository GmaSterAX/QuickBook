package com.softwarearchitecture.QuickBook.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/")
public class PageController {
    
    @GetMapping
    public String showIndexPage() {
        return "index";
    }
    @GetMapping("/about")
    public String showAboutPage() {
        return "/about";
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // templates/register.html
    }
}
