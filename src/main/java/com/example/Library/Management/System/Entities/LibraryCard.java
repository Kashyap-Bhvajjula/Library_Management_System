package com.example.Library.Management.System.Entities;

import com.example.Library.Management.System.Enums.CardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Library_card")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardNo; //This is acting for the primary key of the Library card table

    @Enumerated(value = EnumType.STRING) // Used to define enums in database
    private CardStatus cardStatus;

    private String nameOnCard;

    private Integer noOfBooksIssued;

    //Library card needs to be connected with student table
    @OneToOne
    @JoinColumn
    private Student student;// This is acting as the fk key of the library vcard table

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Transaction> transactionList = new ArrayList<>();






}
