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
        System.out.println("=== getUserInitials ÇALIŞTI! ===");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            System.out.println("Auth problem: " + (auth == null ? "null" : auth.getPrincipal()));
            return "NA";
        }
        
        String email = auth.getName();
        System.out.println("Auth email: " + email);
        
        User user = userRepository.findByMail(email).orElse(null);
        
        if (user == null || user.getName() == null) {
            System.out.println("User not found or name is null");
            return "??";
        }
        
        System.out.println("User name: " + user.getName());
        
        String[] words = user.getName().trim().split("\\s+");
        StringBuilder userInitials = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                userInitials.append(Character.toUpperCase(word.charAt(0)));
            }
        }
        
        String result = userInitials.toString();
        System.out.println("User initials: " + result);
        return result;
    }
}