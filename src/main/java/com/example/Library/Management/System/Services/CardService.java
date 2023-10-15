package com.example.Library.Management.System.Services;

import com.example.Library.Management.System.Entities.LibraryCard;
import com.example.Library.Management.System.Entities.Student;
import com.example.Library.Management.System.Enums.CardStatus;
import com.example.Library.Management.System.Repository.CardRepository;
import com.example.Library.Management.System.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CardService {


    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private StudentRepository studentRepository;

    public LibraryCard generateCard(){

        LibraryCard card = new LibraryCard();

        card.setCardStatus(CardStatus.NEW);

        card = cardRepository.save(card);

        return card;

    }

    public String associateStudentWithCard(Integer studentId, Integer cardNo){

        Optional<Student> studentOptional = studentRepository.findById(studentId);

        Student student = studentOptional.get();

        Optional<LibraryCard> optionalLibraryCard = cardRepository.findById(cardNo);
        LibraryCard libraryCard = optionalLibraryCard.get();

        //Setting the required attributes if the LibraryObject
        libraryCard.setCardStatus(CardStatus.ACTIVE);
        libraryCard.setNameOnCard(student.getName());
        libraryCard.setStudent(student);

        //Setting the attribute of the Student Entitiy
        student.setLibraryCard(libraryCard);

        studentRepository.save(student);

        return "Card with CardNo- "+cardNo+" has been associated to student with StudentId- "+studentId;

    }
}










