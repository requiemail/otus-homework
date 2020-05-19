package ru.otus.homework.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Book;
import ru.otus.homework.repository.BookRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Optional<Book> findByIdWithComments(long id) {
        Optional<Book> bookOptional =  Optional.ofNullable(em.find(Book.class, id));
        bookOptional.ifPresent(book -> book.getComments().size());
        return bookOptional;
    }

}
