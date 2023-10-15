package com.example.Library.Management.System.Services;

import com.example.Library.Management.System.Repository.StudentRepository;
import com.example.Library.Management.System.Entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public String addStudent(Student student){

        studentRepository.save(student);

        return "Student has been saved to the Database";

    }
}
