package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.error.NotFoundException;
import ru.otus.homework.model.Comment;
import ru.otus.homework.repository.CommentRepository;
import ru.otus.homework.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    public Comment getById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Comment with id %d not found", id)));
    }

    @Override
    public List<Comment> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Comment> getAllByBookId(long id) {
        return repository.findAllByBookId(id);
    }
}
