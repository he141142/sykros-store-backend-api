package com.example.sykrosstore.internal.services;

import com.example.sykrosstore.configuration.InitialLoad.GenresInitialLoad;
import com.example.sykrosstore.constants.DatabaseOperation;
import com.example.sykrosstore.constants.EntityValidators.BookValidator;
import com.example.sykrosstore.constants.common.controller.advice.DatabaseOperationException;
import com.example.sykrosstore.constants.common.controller.advice.EntityException;
import com.example.sykrosstore.constants.common.controller.advice.UpdateException;
import com.example.sykrosstore.entities.Books;
import com.example.sykrosstore.entities.Genres;
import com.example.sykrosstore.entities.Subgenres;
import com.example.sykrosstore.internal.controller.dto.book.UpdateBook;
import com.example.sykrosstore.internal.repositories.BookRepositories;
import com.example.sykrosstore.internal.repositories.GenresRepositories;
import com.example.sykrosstore.internal.repositories.SubGenresRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    public ModelMapper modelMapper;
    BookRepositories bookRepositories;

    @Autowired
    GenresInitialLoad genresInitialLoad;
    GenresRepositories genresRepositories;

    SubGenresRepository subgenresLongJpaRepository;
    public BookService(@Autowired BookRepositories _bookRepositories,
                       @Autowired ModelMapper _modelMapper,
                       @Autowired SubGenresRepository subgenresLongJpaRepository,
                       @Autowired
                       GenresRepositories _genresRepositories) {
        this.bookRepositories = _bookRepositories;
        this.modelMapper = _modelMapper;
        this.genresRepositories = _genresRepositories;
        this.subgenresLongJpaRepository = subgenresLongJpaRepository;
    }

    public Books CreateBook(@Valid Books book) {
        book = this.bookRepositories.save(book);
        return book;
    }


    public UpdateBook UpdateBookDetail(UpdateBook updateBook, Long id) throws EntityException, UpdateException {
        Books b = null;
        Optional<Books> book = this.bookRepositories.findById(id);
        if (book.isPresent()) b = updateBook.bindBook(book.get());
        else throw new EntityException(BookValidator.BOOK_NOT_EXIST);
        try {
            this.bookRepositories.save(b);
        } catch (Exception e) {
            throw new UpdateException("cant update database").setEntity("Books");
        }
        return updateBook;
    }

    public List<Genres> loadGenresList() throws DatabaseOperationException {
        try {
            long count = this.genresRepositories.count();
            if (count > 0) throw new DatabaseOperationException("Genres already Loaded")
                    .setOpType(DatabaseOperation.CREATE);
            return genresRepositories.saveAll(this.genresInitialLoad
                    .setUp()
                    .loadGenres()
                    .getGenres());
        } catch (Exception e) {
            throw new DatabaseOperationException("Genres load Failed")
                    .setOpType(DatabaseOperation.CREATE);
        }
    }

    @Override
    public List<Subgenres> listSubGenresByGenresId(Long id) throws DatabaseOperationException {
        try {
            Genres genres = this.genresRepositories.findById(id).orElse(null);
            if (genres==null) throw new EntityException("Error Cant find genres by ID");
            return genres.getSubgenres();
        }catch (Exception e){
            throw new DatabaseOperationException("Genres load Failed")
                    .setOpType(DatabaseOperation.GET);
        }
    }

    @Override
    public boolean isSubGenresExist(Long id){
        return this.genresRepositories.findById(id).orElse(null) != null;
    }
    @Override
    public boolean isBookExist(Long id){
        return this.bookRepositories.findById(id).orElse(null) != null;
    }

   @Override
    public String AddGenresToBook(Long bookID, Long genresID) throws DatabaseOperationException {
        try {
            this.bookRepositories.save(Objects.requireNonNull(this.bookRepositories
                            .findById(bookID)
                            .orElse(null))
                    .addSubGenres(this.subgenresLongJpaRepository
                            .findById(genresID)
                            .orElse(null)));
        }catch (Exception e){
            throw new DatabaseOperationException("SOME THING ERRORS")
                    .setOpType(DatabaseOperation.UPDATE);
        }
        return "Successfully";
    }


}
