package com.softwarearchitecture.QuickBook.Controller;


import com.softwarearchitecture.QuickBook.Dto.NotificationDto;
import com.softwarearchitecture.QuickBook.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping("/get/by/id/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable("id") long notificationId){
        NotificationDto notificationDto = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(notificationDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserId(@PathVariable long userId) {
        List<NotificationDto> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/create")
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationDto notificationDto) {
        NotificationDto createdNotification = notificationService.createNotification(notificationDto);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable long notificationId) {
        notificationService.deleteById(notificationId);
        return ResponseEntity.noContent().build();
    }
}