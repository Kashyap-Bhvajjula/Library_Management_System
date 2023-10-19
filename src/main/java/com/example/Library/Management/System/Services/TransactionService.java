package com.example.Library.Management.System.Services;

import com.example.Library.Management.System.Entities.Book;
import com.example.Library.Management.System.Entities.LibraryCard;
import com.example.Library.Management.System.Entities.Transaction;
import com.example.Library.Management.System.Enums.CardStatus;
import com.example.Library.Management.System.Enums.TransactionStatus;
import com.example.Library.Management.System.Exceptions.BookNotFound;
import com.example.Library.Management.System.Exceptions.CardNotFound;
import com.example.Library.Management.System.Exceptions.InvalidCardStatus;
import com.example.Library.Management.System.Exceptions.MaxBooksAlreadyIssued;
import com.example.Library.Management.System.Repository.BookRepository;
import com.example.Library.Management.System.Repository.CardRepository;
import com.example.Library.Management.System.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final Integer MAX_LIMIT_OF_BOOKS=3;

    private static final Integer FINE_PER_DAY = 5;

    public String issueBook(Integer bookId, Integer cardId) throws Exception{

        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        //Validations

        //Valid BookId
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if(!bookOptional.isPresent()){
            throw new BookNotFound("BookId entered is invalid");
        }

        Book book = bookOptional.get();

        //Availability of book
        if(!book.isAvailable()){
            throw new BookNotFound("Book is not available");

        }

        //Valid cardId
        Optional<LibraryCard> optionalLibraryCard = cardRepository.findById(cardId);

        if(!optionalLibraryCard.isPresent()){
            throw new CardNotFound("CardId entered is Invalid");
        }

        LibraryCard libraryCard = optionalLibraryCard.get();
        //Valid card status

        if(!libraryCard.getCardStatus().equals(CardStatus.ACTIVE)){
            throw new InvalidCardStatus("Card status is not available");
        }

        //Maximum number of books Issues: MAX_LIMIT = 3

        if(libraryCard.getNoOfBooksIssued() == MAX_LIMIT_OF_BOOKS){
            throw new MaxBooksAlreadyIssued(MAX_LIMIT_OF_BOOKS+" is maximum books that can be issued");
        }

        //Creating the Transaction Entity
        transaction.setTransactionStatus(TransactionStatus.ISSUED);

        libraryCard.setNoOfBooksIssued(libraryCard.getNoOfBooksIssued()+1);

        book.setAvailable(false);

        //Setting the Fk
        transaction.setBook(book);
        transaction.setCard(libraryCard);

        //Saving relevant Entities : Bidirectional Mapping
        book.getTransactionList().add(transaction);
        libraryCard.getTransactionList().add(transaction);

        //Instead of saving the parent, just save the child
        transactionRepository.save(transaction);

        return "The book with bookId "+bookId+" has been issued to the card with "+cardId;

    }
    public String returnBook(Integer bookId, Integer cardId){

        Book book = bookRepository.findById(bookId).get();

        LibraryCard card = cardRepository.findById(cardId).get();

        //I need to find out the issued transaction

        Transaction transaction = transactionRepository.findTransactionByBookAndCardAndTransactionStatus(book, card, TransactionStatus.ISSUED);

        Date issueDate = transaction.getCreatedOn();

        //Predefined method that can be used to calculate days
        long milliSeconds = Math.abs(System.currentTimeMillis()-issueDate.getTime());
        long days = TimeUnit.DAYS.convert(milliSeconds, TimeUnit.MILLISECONDS);

        int fineAmount = 0;
        if(days>15){
            fineAmount = Math.toIntExact ((days-15)*FINE_PER_DAY);
        }

        Transaction newTransaction = new Transaction();

        newTransaction.setTransactionStatus(TransactionStatus.COMPLETED);

        newTransaction.setReturnDate(new Date());
        newTransaction.setFine(fineAmount);

        //Setting the foreign keys
        newTransaction.setBook(book);
        newTransaction.setCard(card);

        book.setAvailable(true);
        card.setNoOfBooksIssued(card.getNoOfBooksIssued()-1);

        book.getTransactionList().add(transaction);
        card.getTransactionList().add(transaction);

        transactionRepository.save(newTransaction);

        return "Book has been returned";
    }
}