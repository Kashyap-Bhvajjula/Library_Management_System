package com.example.Library.Management.System.Services;

import com.example.Library.Management.System.Entities.Author;
import com.example.Library.Management.System.Entities.Book;
import com.example.Library.Management.System.Enums.Genre;
import com.example.Library.Management.System.Exceptions.AuthorNotFoundException;
import com.example.Library.Management.System.Repository.AuthorRepository;
import com.example.Library.Management.System.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public String addBook(Book book, Integer authorId) throws Exception {

        Optional<Author> optionalAuthor = authorRepository.findById(authorId);

        if(!optionalAuthor.isPresent()){
            throw new AuthorNotFoundException("Author Id Entered is invalid");
        }

        Author author = optionalAuthor.get();

        book.setAuthor(author);

        //As it's a bidirectional mapping:
        //Author should also have the information of the Book entity

        author.getBookList().add(book);

        /*Now book and author entity both have been modified:
        1. Should I save both
        Should I save only book
        Should I say only author, and why ?
        Ans: Only author should be saved as author will be having the information of the book
        due to bidirectional mapping cascade effect will come to place and book will automatically be saved */

        authorRepository.save(author);

        return "Book has been added to the DataBase";

    }

    public List<String> getBooksByGenre (Genre genre){

        List<Book> bookList = bookRepository.findBooksByGenre(genre);
        List<String> bookNames = new ArrayList<>();

        for(Book book: bookList){
            bookNames.add(book.getBookName());
        }
        return bookNames;
    }






}
