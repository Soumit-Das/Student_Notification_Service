package com.sms.Student_Management_System.Service;

import com.sms.Student_Management_System.Model.Notifications;
import com.sms.Student_Management_System.Model.Students;

import java.util.List;

public interface StudentService {

    public Students updateStudentById(int id,Students student);

    public Students saveStudent(Students students);

    public void sendNotification(Students students);

    public List<Notifications> getAllNotifications();

    public List<Notifications> getAllNotificationsPagewise(int pageNumber, int numberOfData);


}
