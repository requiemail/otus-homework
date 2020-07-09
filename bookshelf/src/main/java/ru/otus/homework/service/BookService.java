package ru.otus.homework.service;

import ru.otus.homework.model.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);
    Book getById(Long id);
    Book getByIdWithComments(Long id);
    List<Book> getAll();
    void delete(Book book);
}
