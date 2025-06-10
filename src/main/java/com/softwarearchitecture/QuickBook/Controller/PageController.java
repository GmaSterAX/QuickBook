package com.softwarearchitecture.QuickBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String getHomePage(){
        return "index";
    }

    @GetMapping("/index")
    public String indexPage() {
    return "redirect:/"; 
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "about";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "reservation";
    }
    @GetMapping("/change-password")
    public String getChangePasswordPage(){
        return "change-password";
    }
}
