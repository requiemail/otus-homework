package ru.otus.homework.service;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.homework.model.Comment;

import java.util.List;

public interface CommentService {

    @PreAuthorize("hasAuthority('READER')")
    Comment getById(Long id);

    @PreAuthorize("hasAuthority('READER')")
    List<Comment> getAll();

}
