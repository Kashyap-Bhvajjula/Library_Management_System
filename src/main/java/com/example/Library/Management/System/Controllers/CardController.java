package com.example.Library.Management.System.Controllers;


import com.example.Library.Management.System.Entities.LibraryCard;
import com.example.Library.Management.System.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")

public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/generatePlainCard")
    public ResponseEntity generatingPlainCard()
    {


        LibraryCard newCard = cardService.generateCard();

        String response = "The new Card has been generated and having a cardNo "+newCard.getCardNo();

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @PutMapping("/associateWithStudent")
    public ResponseEntity associateWithStudent(@RequestParam("studentId")Integer studentId, @RequestParam("cardId")Integer cardId){

        String result = cardService.associateStudentWithCard(studentId, cardId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}











