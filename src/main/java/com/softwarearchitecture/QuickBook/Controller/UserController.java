package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.NotificationDto;
import com.softwarearchitecture.QuickBook.Dto.UserDto;
import com.softwarearchitecture.QuickBook.Service.NotificationService;
import com.softwarearchitecture.QuickBook.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final NotificationService notificationService;

    @Autowired
    public UserController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    // CREATE User
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // GET User by ID
    @GetMapping("/my-account")
    public String getMyAccount(Model model) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        UserDto user = userService.getUserByMail(userMail);
        model.addAttribute("user", user);
        return "my-account";
    }

    // GET All Users
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // UPDATE User
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("id") long userId,
            @RequestBody UserDto updatedUser) {
        UserDto userDto = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(userDto);
    }

    // DELETE User
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/notifications")
    public String getUserNotifications(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        Long userId = userService.getUserByMail(userMail).getId();
        List<NotificationDto> notifications = notificationService.getNotificationsByUserId(userId);
        model.addAttribute("notifications", notifications);
        return "notifications";
    }
}
