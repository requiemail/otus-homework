package ru.otus.homework.repository;

import ru.otus.homework.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Genre save(Genre genre);
    Optional<Genre> findById(long id);
    List<Genre> findAll();

}
