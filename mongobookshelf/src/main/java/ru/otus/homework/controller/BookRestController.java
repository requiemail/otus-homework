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
import ru.otus.homework.model.Book;
import ru.otus.homework.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService service;

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") String id){
        return service.getById(id);
    }

    @PostMapping
    public Book create (@RequestBody Book book) {
        return service.save(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable("id") String id, @Valid @RequestBody Book book) {
        return service.save(book);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        service.delete(service.getById(id));
        return id;
    }

}
