package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.error.NotFoundException;
import ru.otus.homework.model.Author;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Override
    public Author save(Author author) {
        return repository.save(author);
    }

    @Override
    public Author getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Author with id %d not found", id)));
    }

    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }

}
