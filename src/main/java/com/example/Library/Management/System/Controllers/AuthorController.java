package com.example.Library.Management.System.Controllers;


import com.example.Library.Management.System.Entities.Author;
import com.example.Library.Management.System.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @PostMapping("/add")
    public ResponseEntity<String> addAuthor(@RequestBody Author author){

        String result = authorService.addAuthor(author);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/findAllAuthorNames")
    public List<String> getAllAuthorNames(){

        return authorService.getAllAuthorNames();

    }

    @GetMapping("/getAuthor/{id}")
    public ResponseEntity getAuthorById (@PathVariable Integer id){

        try{

            Author author = authorService.getAuthorById(id);
            return new ResponseEntity(author, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
