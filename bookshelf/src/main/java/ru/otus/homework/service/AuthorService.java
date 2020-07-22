package ru.otus.homework.service;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.homework.model.Author;

import java.util.List;

public interface AuthorService {

    @PreAuthorize("hasAuthority('WRITER')")
    Author save(Author author);

    @PreAuthorize("hasAuthority('READER')")
    Author getById(Long id);

    @PreAuthorize("hasAuthority('READER')")
    List<Author> getAll();

}
