package ru.otus.homework.service;

import ru.otus.homework.model.Author;

import java.util.List;

public interface AuthorService {

    Author save(Author author);

    Author getById(String id);

    List<Author> getAll();

}