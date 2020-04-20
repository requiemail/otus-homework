package ru.otus.homework.dao;

import ru.otus.homework.model.Genre;

import java.util.List;

public interface GenreDao {

    int insert(Genre genre);
    Genre findById(long id);
    Genre findByName(String name);
    List<Genre> findAll();
    List<Genre> findAllByBookId(long id);
    int update(Genre genre);

}
