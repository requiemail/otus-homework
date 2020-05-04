package ru.otus.homework.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.model.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(BookDaoImpl.class)
@DisplayName("DAO книг должен:")
class BookDaoImplTest {

    @Autowired
    private BookDaoImpl dao;

    @Test
    @DisplayName("возвращать из базы сохраненную книгу;")
    void shouldReturnInsertedAuthor() {
        long insertedBookId = dao.insert(Book.builder()
                .name("Test Book")
                .isbnCode("111-11-111")
                .publicationYear("2020")
                .build());
        assertThat(dao.findById(insertedBookId)).extracting("name", "isbnCode", "publicationYear")
                .contains("Test Book", "111-11-111", "2020");
    }

    @Test
    @DisplayName("возвращать из базы книгу по её ID;")
    void shouldReturnBookById() {
        assertThat(dao.findById(1).getName()).isEqualTo("American Gods");
    }

    @Test
    @DisplayName("возвращать из базы книгу по её названию;")
    void shouldReturnBookByName() {
        assertThat(dao.findByName("American Gods").getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("возвращать из базы полный список книг;")
    void shouldReturnFullListOfStoredBooks() {
        assertThat(dao.findAll().size()).isGreaterThanOrEqualTo(6);
    }

    @Test
    @DisplayName("изменять название, ISBN код и год издания;")
    void update() {
        long updatedBookId = dao.update(Book.builder()
                .id(1)
                .name("Test Book")
                .isbnCode("111-11-111")
                .publicationYear("2020")
                .build());
        assertThat(dao.findById(updatedBookId)).extracting("name", "isbnCode", "publicationYear")
                .contains("Test Book", "111-11-111", "2020");
    }

    @Test
    @DisplayName("кидать исключение при попытке поиска удаленной книги;")
    void shouldThrowExceptionAfterBookDelete() {
        long insertedBookId = dao.insert(Book.builder()
                .name("Test Book")
                .isbnCode("111-11-111")
                .publicationYear("2020")
                .build());
        dao.deleteById(insertedBookId);
        assertThrows(EmptyResultDataAccessException.class, () -> dao.findById(insertedBookId));
    }
}