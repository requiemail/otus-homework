package ru.otus.homework.dao;

import ru.otus.homework.model.Book;

import java.util.List;

public interface BookDao {

    long insert(Book book);
    Book findById(long id);
    Book findByName(String name);
    List<Book> findAll();
    int update(Book book);
    int deleteById(long id);

}
