package com.example.sykrosstore.internal.services;

import com.example.sykrosstore.constants.common.controller.advice.DatabaseOperationException;
import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.constants.common.controller.advice.UpdateException;
import com.example.sykrosstore.entities.Books;
import com.example.sykrosstore.entities.Genres;
import com.example.sykrosstore.entities.Subgenres;
import com.example.sykrosstore.internal.controller.dto.book.UpdateBook;

import javax.validation.Valid;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public interface IBookService {

    Books CreateBook(@Valid Books book);
    UpdateBook UpdateBookDetail(UpdateBook updateBook, Long id) throws EntityException, UpdateException;
    List<Genres> loadGenresList() throws XMLStreamException, IOException, DatabaseOperationException;
    List<Subgenres> listSubGenresByGenresId(Long id) throws DatabaseOperationException;

    boolean isSubGenresExist(Long id);

    boolean isBookExist(Long id);

    String AddGenresToBook(Long bookID, Long genresID) throws DatabaseOperationException;
}
