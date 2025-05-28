package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findById(Long notificationId);
    List<Notification> findAllByUserId(long userId);
}
