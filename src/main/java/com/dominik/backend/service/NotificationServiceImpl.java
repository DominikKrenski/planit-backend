package com.dominik.backend.service;

import com.dominik.backend.entity.Notification;
import com.dominik.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}
