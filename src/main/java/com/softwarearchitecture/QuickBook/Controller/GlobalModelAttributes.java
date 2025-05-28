package com.softwarearchitecture.QuickBook.Controller; // Controller klasöründen çıkar

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;

@ControllerAdvice
public class GlobalModelAttributes {
    
    @Autowired
    private UserRepository userRepository;
    
    @ModelAttribute("userInitials")
    public String getUserInitials() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return "NA";
        }
        
        String email = auth.getName();
        
        User user = userRepository.findByMail(email).orElse(null);
        
        if (user == null || user.getName() == null) {
            return "??";
        }
        
        String[] words = user.getName().trim().split("\\s+");
        StringBuilder userInitials = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                userInitials.append(Character.toUpperCase(word.charAt(0)));
            }
        }
        
        String result = userInitials.toString();
        return result;
    }
}