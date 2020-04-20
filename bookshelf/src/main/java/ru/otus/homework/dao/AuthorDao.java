package ru.otus.homework.dao;

import ru.otus.homework.model.Author;

import java.util.List;

public interface AuthorDao {

    int insert(Author author);
    Author findById(long id);
    Author findByName(String name);
    List<Author> findAll();
    List<Author> findAllByBookId(long id);
    int update(Author author);

}