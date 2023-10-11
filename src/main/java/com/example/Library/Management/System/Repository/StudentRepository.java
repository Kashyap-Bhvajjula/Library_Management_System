package com.example.Library.Management.System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Library.Management.System.Entities.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {


}
