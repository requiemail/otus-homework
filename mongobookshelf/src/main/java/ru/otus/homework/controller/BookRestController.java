package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.model.Book;
import ru.otus.homework.repository.BookRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookRepository repository;

    @GetMapping("/all")
    public Flux<Book> getAllBooks() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Book> getBookById(@PathVariable("id") String id){
        return repository.findById(id);
    }

    @PostMapping
    public Mono<Book> create (@RequestBody Book book) {
        return repository.save(book);
    }

    @PutMapping("/{id}")
    public Mono<Book> update(@PathVariable("id") String id, @Valid @RequestBody Book book) {
        return repository.save(book);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        repository.findById(id).flatMap(repository::delete);
        return id;
    }

}
