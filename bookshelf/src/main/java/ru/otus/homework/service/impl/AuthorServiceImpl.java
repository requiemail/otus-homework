package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.model.Author;
import ru.otus.homework.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao dao;

    @Override
    public long add(Author author) {
        return dao.insert(author);
    }

    @Override
    public Author getById(long id) {
        return dao.findById(id);
    }

    @Override
    public Author getByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public List<Author> getAll() {
        return dao.findAll();
    }

    @Override
    public List<Author> getAllByBookId(long id) {
        return dao.findAllByBookId(id);
    }

}
