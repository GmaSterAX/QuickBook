package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.NotificationDto;
import com.softwarearchitecture.QuickBook.Exception.ResourceNotFoundException;
import com.softwarearchitecture.QuickBook.Mapper.NotificationMapper;
import com.softwarearchitecture.QuickBook.Model.Notification;
import com.softwarearchitecture.QuickBook.Repository.NotificationRepository;
import com.softwarearchitecture.QuickBook.Service.NotificationService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;{this.notificationRepository = notificationRepository;}


    @Override
    public NotificationDto getNotificationById(long notificationId) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        return NotificationMapper.mapToNotificationDto(notification.orElse(null));
    }

    @Override
    public List<NotificationDto> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        return notifications.stream()
                .map(NotificationMapper:: mapToNotificationDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        Notification notification = NotificationMapper.mapToNotification(notificationDto);
        Notification createdNotification = notificationRepository.save(notification);

        return NotificationMapper.mapToNotificationDto(createdNotification);
    }

    @Override
    public NotificationDto updateState(long notificationId, NotificationDto newState) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new ResourceNotFoundException("Notification is not found with given id : " + notificationId));

        notification.setState(newState.getState());

        Notification updatedNotification = notificationRepository.save(notification);
        return NotificationMapper.mapToNotificationDto(updatedNotification);
    }

    @Override
    public void deleteById(long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new ResourceNotFoundException("Notification is not found :" + notificationId));
        notificationRepository.deleteById(notificationId);

    }
}
