package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.model.Genre;
import ru.otus.homework.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao dao;

    @Override
    public long add(Genre genre) {
        return dao.insert(genre);
    }

    @Override
    public Genre getById(long id) {
        return dao.findById(id);
    }

    @Override
    public Genre getByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public List<Genre> getAll() {
        return dao.findAll();
    }

    @Override
    public List<Genre> getAllByBookId(long id) {
        return dao.findAllByBookId(id);
    }

    @Override
    public int update(Genre genre) {
        return dao.update(genre);
    }

}
