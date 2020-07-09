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
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    @Transactional
    public Book save(Book book) {

        Optional<Set<Author>> authorsOptional = Optional.ofNullable(book.getAuthorList());
        authorsOptional.ifPresent(authors -> authors.forEach(authorService::save));

        Optional<Set<Genre>> genreOptional = Optional.ofNullable(book.getGenreList());
        genreOptional.ifPresent(genres -> genres.forEach(genreService::save));

        return repository.save(book);
    }

    @Override
    public Book getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id %d not found", id)));
    }

    @Override
    public Book getByIdWithComments(Long id) {
        return repository.findByIdWithComments(id)
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
