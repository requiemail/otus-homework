package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.error.NotFoundException;
import ru.otus.homework.model.Book;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    public Book getById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id %d not found", id)));
    }

    @Override
    public Book getByIdWithComments(long id) {
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
