package ru.otus.homework.service;

import ru.otus.homework.model.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);
    Book getById(long id);
    Book getByIdWithComments(long id);
    List<Book> getAll();
    void delete(Book book);
}
