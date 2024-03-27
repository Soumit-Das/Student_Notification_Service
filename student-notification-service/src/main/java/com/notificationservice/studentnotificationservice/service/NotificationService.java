package com.notificationservice.studentnotificationservice.service;

import com.notificationservice.studentnotificationservice.models.Notifications;
import com.notificationservice.studentnotificationservice.models.Students;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationService {

    public Notifications createNotification(Students students);

    public List<Notifications> getAllNotifications();

    public List<Notifications> getAllNotificationsPagewise(int pageNumber, int numberOfData);

}
