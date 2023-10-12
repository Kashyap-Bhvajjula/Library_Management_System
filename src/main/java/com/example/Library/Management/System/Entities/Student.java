package com.example.Library.Management.System.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity// This means it is a structure that will be reflected in database
@Table(name = "student")// This class will have a table whose name is student
@Getter
@Setter
@AllArgsConstructor //Constructor for all Arguments in the table
@NoArgsConstructor //Constructor for no arguments in the table ----Student(){}
public class Student {

    @Id
    private Integer studentId; // This will behave as the primary key

    private String name;

    private int age;

    @Column(name ="Contact Number", unique = true, nullable = false) // Used to modify the column attributes
    private String mobNo;

    private String emailId; // Camel case is converted to snake case automatically (emailID -> email_id)

    private String bloodGroup;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private LibraryCard libraryCard;

    //This should also have libraryCard information

}
