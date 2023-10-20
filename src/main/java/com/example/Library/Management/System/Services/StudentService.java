package com.example.Library.Management.System.Services;

import com.example.Library.Management.System.Repository.StudentRepository;
import com.example.Library.Management.System.Entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JavaMailSender mailSender;

    public String addStudent(Student student){

        studentRepository.save(student);

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String body = "Hello "+student.getName()+"! You have successfully registered to Spring School's Library. You can start issuing the books now :)";

        mailMessage.setFrom("bkprojects880@gmail.com");
        mailMessage.setTo(student.getEmailId());
        mailMessage.setSubject("Welcome to Spring School's Library");
        mailMessage.setText(body);

        mailSender.send(mailMessage);

        return "Student has been saved to the Database";

    }
}
