package ru.otus.homework.service;

import ru.otus.homework.model.Genre;

import java.util.List;

public interface GenreService {

    Genre save(Genre genre);
    Genre getById(long id);
    List<Genre> getAll();

}
