package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.model.Comment;
import ru.otus.homework.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService service;

    @GetMapping("/all")
    public List<Comment> getAllComments() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Comment getBookById(@PathVariable("id") Long id){
        return service.getById(id);
    }
}
