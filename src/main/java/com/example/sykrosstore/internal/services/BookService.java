package com.example.sykrosstore.internal.services;

import com.example.sykrosstore.constants.EntityValidators.BookValidator;
import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.constants.common.controller.advice.UpdateException;
import com.example.sykrosstore.entities.BookDetail;
import com.example.sykrosstore.entities.Books;
import com.example.sykrosstore.internal.controller.dto.book.UpdateBook;
import com.example.sykrosstore.internal.repositories.BookRepositories;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    public ModelMapper modelMapper;
    BookRepositories bookRepositories;

    public BookService(@Autowired
                       BookRepositories _bookRepositories,
                       @Autowired ModelMapper _modelMapper) {
        this.bookRepositories = _bookRepositories;
        this.modelMapper = _modelMapper;
    }

    public Books CreateBook(@Valid Books book) {
        book = this.bookRepositories.save(book);
        return book;
    }


    public UpdateBook UpdateBookDetail(UpdateBook updateBook, Long id) throws EntityException, UpdateException {
        Books b = null;
        Optional<Books> book = this.bookRepositories.findById(id);
        if (book.isPresent()) {
            b = updateBook.bindBook(book.get());
        } else {
            throw new EntityException(BookValidator.BOOK_NOT_EXIST);
        }
        try {
            this.bookRepositories.save(b);
        }catch (Exception e){
            throw new UpdateException("cant update database").setEntity("Books");
        }
        return updateBook;
    }


}
