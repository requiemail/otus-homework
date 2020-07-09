package ru.otus.homework.service;

import ru.otus.homework.model.Comment;

import java.util.List;

public interface CommentService {

    Comment getById(Long id);
    List<Comment> getAll();

}
