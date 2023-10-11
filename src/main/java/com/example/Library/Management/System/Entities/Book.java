package com.example.Library.Management.System.Entities;

import com.example.Library.Management.System.Enums.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity //This is basically a model class
@Table//In case you don't write ane name, the class name is taken as the table name
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id //Id is denoted as the primary key of the table
    private Integer bookId;//This is the primary key of the table

    private String bookName;

    private int price;

    private int noOfPages;

    private Genre genre;

    //Author

}
