package ru.otus.homework.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Book;
import ru.otus.homework.repository.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("from Book", Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(Book book) {
        book = em.merge(book);
        em.remove(book);
    }

    @Override
    @Transactional
    public Optional<Book> findByIdWithComments(long id) {
        Optional<Book> bookOptional =  Optional.ofNullable(em.find(Book.class, id));
        bookOptional.ifPresent(book -> book.getComments().size());
        return bookOptional;
    }

}
