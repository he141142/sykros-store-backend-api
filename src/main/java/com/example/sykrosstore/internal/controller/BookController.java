package com.example.sykrosstore.internal.controller;

import com.example.sykrosstore.constants.common.controller.AdminPrefix;
import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.entities.Books;
import com.example.sykrosstore.internal.controller.dto.book.UpdateBook;
import com.example.sykrosstore.internal.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "book")
public class BookController {

    public IBookService bookService;

    public BookController(@Autowired IBookService _bookService) {
        this.bookService = _bookService;
    }

    @RequestMapping( value = "create", method = RequestMethod.POST)
    public Books createBook(@Valid @RequestBody Books books) {
        return this.bookService.CreateBook(books);
    }

    @RequestMapping(value = "update", method = RequestMethod.PATCH)
    public UpdateBook updateBookDetail(@Valid @RequestBody UpdateBook updateBook,@Param("id") Long id) throws EntityException {
        this.bookService.UpdateBookDetail(updateBook,id);
        return updateBook;
    }


}
