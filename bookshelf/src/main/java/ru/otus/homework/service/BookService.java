package ru.otus.homework.service;

import ru.otus.homework.model.Book;

import java.util.List;

public interface BookService {

    Book add(Book book);
    Book getById(long id);
    List<Book> getAll();
    int update(Book book);
    int delete(long id);
}
