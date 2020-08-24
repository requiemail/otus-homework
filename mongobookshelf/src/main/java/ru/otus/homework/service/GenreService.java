package ru.otus.homework.service;

import ru.otus.homework.model.Genre;

import java.util.List;

public interface GenreService {

    Genre save(Genre genre);

    Genre getById(String id);

    List<Genre> getAll();

}
