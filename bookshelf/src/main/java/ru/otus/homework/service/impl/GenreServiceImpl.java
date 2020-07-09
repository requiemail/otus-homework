package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.error.NotFoundException;
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
    public Genre getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Genre with id %d not found", id)));
    }

    @Override
    public List<Genre> getAll() {
        return repository.findAll();
    }

}
