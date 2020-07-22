package ru.otus.homework.service;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface GenreService {

    @PreAuthorize("hasAuthority('WRITER')")
    Genre save(Genre genre);

    @PreAuthorize("hasAuthority('READER')")
    Genre getById(Long id);

    @PreAuthorize("hasAuthority('READER')")
    List<Genre> getAll();

}
