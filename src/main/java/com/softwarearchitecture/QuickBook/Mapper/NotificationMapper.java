package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.NotificationDto;
import com.softwarearchitecture.QuickBook.Model.Notification;
import com.softwarearchitecture.QuickBook.Model.User;

public class NotificationMapper {

    public static NotificationDto mapToNotificationDto(Notification notification) {
        return new NotificationDto(
                notification.getId(),
                notification.getMessage(),
                notification.getMessageTitle(),
                notification.getUser().getId()
        );
    }

    public static Notification mapToNotification(NotificationDto notificationDto){
        Notification notification = new Notification(
                notificationDto.getId(),
                notificationDto.getMessage(),
                notificationDto.getMessageTitle()
        );

        User user = new User();
        user.setId(notificationDto.getUser_id()); 
        notification.setUser(user);

        return notification;
    }
}

