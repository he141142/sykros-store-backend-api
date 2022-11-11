package com.example.sykrosstore.internal.services;

import com.example.sykrosstore.configuration.InitialLoad.GenresInitialLoad;
import com.example.sykrosstore.constants.DatabaseOperation;
import com.example.sykrosstore.constants.EntityValidators.BookValidator;
import com.example.sykrosstore.constants.common.controller.advice.DatabaseOperationException;
import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.constants.common.controller.advice.UpdateException;
import com.example.sykrosstore.entities.BookDetail;
import com.example.sykrosstore.entities.Books;
import com.example.sykrosstore.entities.Genres;
import com.example.sykrosstore.internal.controller.dto.book.UpdateBook;
import com.example.sykrosstore.internal.repositories.BookRepositories;
import com.example.sykrosstore.internal.repositories.GenresRepositories;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    public ModelMapper modelMapper;
    BookRepositories bookRepositories;

    @Autowired
    GenresInitialLoad genresInitialLoad;

    GenresRepositories genresRepositories;

    public BookService(@Autowired
                       BookRepositories _bookRepositories,
                       @Autowired ModelMapper _modelMapper,

                       @Autowired
                       GenresRepositories _genresRepositories) {
        this.bookRepositories = _bookRepositories;
        this.modelMapper = _modelMapper;
        this.genresRepositories = _genresRepositories;
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
        } catch (Exception e) {
            throw new UpdateException("cant update database").setEntity("Books");
        }
        return updateBook;
    }

    public List<Genres> loadGenresList() throws  DatabaseOperationException {
        System.out.println(this.genresInitialLoad.getLocation());
        try {
            return genresRepositories.saveAll(this.genresInitialLoad
                    .setUp()
                    .loadGenres()
                    .getGenres());
        } catch (Exception e) {
            throw new DatabaseOperationException("Genres load Failed")
                    .setOpType(DatabaseOperation.CREATE);
        }
    }


}
