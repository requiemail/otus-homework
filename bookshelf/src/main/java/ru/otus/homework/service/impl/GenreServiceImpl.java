package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.GenreRepository;
import ru.otus.homework.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    @Override
    public Genre save(Genre genre) {
        return repository.save(genre);
    }

    @Override
    public Genre getById(long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Genre> getAll() {
        return repository.findAll();
    }

}
