package com.example.sykrosstore.internal.controller;

import com.example.sykrosstore.constants.common.controller.advice.DatabaseOperationException;
import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.constants.common.controller.advice.UpdateException;
import com.example.sykrosstore.constants.enums.CaseMode;
import com.example.sykrosstore.custom.responseEntity.Message;
import com.example.sykrosstore.entities.Books;
import com.example.sykrosstore.entities.Subgenres;
import com.example.sykrosstore.internal.controller.dto.book.UpdateBook;
import com.example.sykrosstore.internal.decoreators.interfaces.IsEntityExist;
import com.example.sykrosstore.internal.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "book")
public class BookController {

    public IBookService bookService;

    public BookController(@Autowired IBookService _bookService) {
        this.bookService = _bookService;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Books createBook(@Valid @RequestBody Books books) {
        return this.bookService.CreateBook(books);
    }

    @RequestMapping(value = "update", method = RequestMethod.PATCH)
    public UpdateBook updateBookDetail(@Valid @RequestBody UpdateBook updateBook, @Param("id") Long id) throws EntityException, UpdateException {
        this.bookService.UpdateBookDetail(updateBook, id);
        return updateBook;
    }

    @RequestMapping(value = "/genres/list/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> ListSubgenresByGenresId(
            @Valid @NotNull @PathVariable(name = "id") Long id
    ) throws DatabaseOperationException {
        List<Subgenres> genresList = this.bookService.listSubGenresByGenresId(id);
        String genresName = genresList.get(0).getGenres().getName();
        return new ResponseEntity<>(new Message.builderMessage<List<Subgenres>>()
                .httpStatus(HttpStatus.OK)
                .setData(genresList)
                .setMsg("ID: 1, Genres Name: " + genresName)
                .buildMsgData(), HttpStatus.OK);
    }


    @RequestMapping(value = "/{book-id}/subgenres/{subgenres-id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> addGenresToBook(
            @Valid @IsEntityExist(entity = CaseMode.BOOK) @PathVariable(name = "book-id")  Long bookId,
            @Valid @IsEntityExist(entity = CaseMode.SUBGENRES) @PathVariable(name = "subgenres-id")  Long subGenresId
    ) throws DatabaseOperationException {
        return new ResponseEntity<>(new Message
                .builderMessage<String>().setMsg(
                this.bookService.AddGenresToBook(bookId, subGenresId)
        ), HttpStatus.OK);
    }


}
