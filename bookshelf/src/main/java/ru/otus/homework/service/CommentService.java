package ru.otus.homework.service;

import ru.otus.homework.model.Comment;

import java.util.List;

public interface CommentService {

    Comment getById(long id);
    List<Comment> getAll();


    List<Comment> getAllByBookId(long id);
}
