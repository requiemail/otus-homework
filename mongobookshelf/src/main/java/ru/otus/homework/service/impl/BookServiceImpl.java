package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.error.NotFoundException;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    @Transactional
    public Book save(Book book) {

        return repository.save(book);
    }

    @Override
    public Book getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id %d not found", id)));
    }

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Book book) {
        repository.delete(book);
    }

}
