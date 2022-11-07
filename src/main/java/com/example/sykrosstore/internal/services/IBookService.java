package com.example.sykrosstore.internal.services;

import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.entities.Books;
import com.example.sykrosstore.internal.controller.dto.book.UpdateBook;

import javax.validation.Valid;

public interface IBookService {

    Books CreateBook(@Valid Books book);
    UpdateBook UpdateBookDetail(UpdateBook updateBook, Long id) throws EntityException;
}
