package ru.otus.homework.repository;

import ru.otus.homework.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);
    Optional<Comment> findById(long id);
    List<Comment> findAll();
    List<Comment> findAllByBookId(long id);
}