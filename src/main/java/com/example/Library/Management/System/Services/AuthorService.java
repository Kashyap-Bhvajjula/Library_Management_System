package com.example.Library.Management.System.Services;

import com.example.Library.Management.System.Entities.Author;
import com.example.Library.Management.System.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.lang.Exception;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public String addAuthor(Author author){

        authorRepository.save(author);
        return "Author has been added to the Database";


    }

    public List<String> getAllAuthorNames(){

        List<Author> authors = authorRepository.findAll();

        List<String> authorNames = new ArrayList<>();

        for(Author author: authors){
            authorNames.add(author.getAuthorName());
        }
        return authorNames;

    }

    public Author getAuthorById(Integer Id) throws Exception {

        //Optional Author can have a value of not have a value, to save from null pointer exception
        Optional<Author> optionalAuthor = authorRepository.findById(Id);
        if(!optionalAuthor.isPresent()){
            //Throw an error
            throw new Exception("The id entered is invalid");
        }

        Author author = optionalAuthor.get();//Get is used to get the entity

        return author;


    }
}





