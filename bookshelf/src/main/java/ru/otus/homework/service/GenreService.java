package ru.otus.homework.service;

import ru.otus.homework.model.Genre;

import java.util.List;

public interface GenreService {

    long add(Genre genre);
    Genre getById(long id);
    Genre getByName(String name);
    List<Genre> getAll();
    List<Genre> getAllByBookId(long id);
    int update(Genre genre);

}
