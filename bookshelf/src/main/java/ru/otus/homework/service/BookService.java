package ru.otus.homework.service;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.homework.model.Book;

import java.util.List;

public interface BookService {

    @PreAuthorize("hasAuthority('WRITER')")
    Book save(Book book);

    @PreAuthorize("hasAuthority('READER')")
    Book getById(Long id);

    @PreAuthorize("hasAuthority('READER')")
    Book getByIdWithComments(Long id);

    @PreAuthorize("hasAuthority('READER')")
    List<Book> getAll();

    @PreAuthorize("hasAuthority('WRITER')")
    void delete(Book book);
}
