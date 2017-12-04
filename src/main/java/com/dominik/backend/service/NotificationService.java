package com.dominik.backend.service;

import com.dominik.backend.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification saveNotification(Notification notification);
    void deleteNotificationById(Long id);
    List<Notification> getAllNotifications();
    Notification getNotificationById(Long id);
}
