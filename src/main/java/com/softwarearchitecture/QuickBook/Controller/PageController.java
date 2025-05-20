package com.softwarearchitecture.QuickBook.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.softwarearchitecture.QuickBook.Service.HotelService;

@Controller
public class PageController {

    HotelService hotelService;

    @Autowired
    public PageController(HotelService hotelService){
        this.hotelService = hotelService;
    }
    
    @GetMapping("/index")
    public String getHomePage(){
        return "index";
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
}
