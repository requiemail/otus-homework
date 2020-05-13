package ru.otus.homework.repository;

import ru.otus.homework.model.Book;

import java.util.Optional;

public interface BookRepositoryCustom{

    Optional<Book> findByIdWithComments(long id);

}
