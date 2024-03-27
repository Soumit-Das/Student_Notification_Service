package com.sms.Student_Management_System.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.Student_Management_System.Model.Notifications;
import com.sms.Student_Management_System.Model.Students;
import com.sms.Student_Management_System.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentsServiceImpl implements StudentService{

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Students updateStudentById(int id, Students student) {

        Optional<Students> s = studentRepository.findById(id);

        Students st = s.get();

        st.setName(student.getName());
        st.setAddress(student.getAddress());
        st.setBatch(student.getBatch());
        st.setAge(student.getAge());
        st.setCreatedAt(new Date());
        sendNotification(student);
        return studentRepository.save(st);
    }

    @Override
    public Students saveStudent(Students students) {
        sendNotification(students);

        Optional<Students> stu = studentRepository.findById(students.getStudentId());

        students.setCreatedAt(new Date());
        return studentRepository.save(students);

    }


    @Override
    public void sendNotification(Students students) {

        //RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8082/notifications/sendNotification";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Students> requestEntity = new HttpEntity<>(students,headers);
        restTemplate.postForObject(url,requestEntity,Students.class);

    }

    @Override
    public List<Notifications> getAllNotifications() {

        String url = "http://localhost:8082/notifications/getAllNotifications";
        return restTemplate.getForObject(url, List.class);

    }

    @Override
    public List<Notifications> getAllNotificationsPagewise(int pageNumber, int numberOfData) {
        String url = String.format("http://localhost:8082/notifications/getAllNotificationsPagewise?pageNumber=%d&numberOfData=%d", pageNumber, numberOfData);

        // Adjusted to directly map the JSON array to a List<Notifications>
        ResponseEntity<List<Notifications>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();

    }

}
