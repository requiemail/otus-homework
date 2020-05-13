package ru.otus.homework.repository;

import ru.otus.homework.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);
    Optional<Author> findById(long id);
    List<Author> findAll();

}
