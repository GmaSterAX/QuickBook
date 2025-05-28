package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    NotificationDto getNotificationById(long notificationId);
    List<NotificationDto> getNotificationsByUserId(Long userId);
    NotificationDto createNotification(NotificationDto notificationDto);
    void deleteById(long id);


}
