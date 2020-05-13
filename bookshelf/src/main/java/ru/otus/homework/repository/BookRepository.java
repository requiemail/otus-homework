package ru.otus.homework.repository;

import ru.otus.homework.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);
    Optional<Book> findById(long id);
    List<Book> findAll();
    void delete(Book book);
    Optional<Book> findByIdWithComments(long id);

}
