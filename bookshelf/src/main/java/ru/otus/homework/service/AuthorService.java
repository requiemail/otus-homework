package ru.otus.homework.service;

import ru.otus.homework.model.Author;

import java.util.List;

public interface AuthorService {

    int add(Author author);
    Author getById(long id);
    Author getByName(String name);
    List<Author> getAll();
    List<Author> getAllByBookId(long id);

}
