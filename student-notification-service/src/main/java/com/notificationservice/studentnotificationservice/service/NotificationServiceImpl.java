package com.notificationservice.studentnotificationservice.service;

import com.notificationservice.studentnotificationservice.models.Notifications;
import com.notificationservice.studentnotificationservice.models.Students;
import com.notificationservice.studentnotificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    public NotificationRepository notificationRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Notifications createNotification(Students students) {

        Notifications notifications = new Notifications();
        notifications.setNotificationId(7);
        notifications.setNotificationType("adding student");
        notifications.setPayload(copy(students));
        notifications.setCreatedOn(new Date());

        return notificationRepository.save(notifications);

    }

    public Students copy(Students student)
    {
        Students st = new Students();
        st.setName(student.getName());
        st.setAddress(student.getAddress());
        st.setAge(student.getAge());
        st.setBatch(student.getBatch());
        st.setCreatedAt(new Date());
        return student;
    }



    @Override
    public List<Notifications> getAllNotifications(){

        List<Notifications> notifications = notificationRepository.findAll();

        return notifications;
    }


    @Override
    public List<Notifications> getAllNotificationsPagewise(int pageNumber, int numberOfData) {

        PageRequest pageRequest = PageRequest.of(pageNumber-1,numberOfData);
        return notificationRepository.findAll(pageRequest).getContent();

    }


}
