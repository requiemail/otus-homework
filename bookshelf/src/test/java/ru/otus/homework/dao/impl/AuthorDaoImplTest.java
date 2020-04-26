package ru.otus.homework.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorDaoImpl.class)
@DisplayName(value = "DAO авторов должен:")
class AuthorDaoImplTest {

    @Autowired
    private AuthorDaoImpl dao;

    @Test
    @DisplayName(value = "возвращать из базы сохраненного автора;")
    void shouldReturnInsertedAuthor() {
        long insertedAuthor = dao.insert(Author.builder().name("Test Author").build());
        assertThat(dao.findById(insertedAuthor).getName()).isEqualTo("Test Author");
    }

    @Test
    @DisplayName(value = "возвращать из базы автора по его ID;")
    void shouldReturnAuthorById() {
        assertThat(dao.findById(1).getName()).isEqualTo("Neil Gaiman");
    }

    @Test
    @DisplayName(value = "возвращать из базы автора по его имени;")
    void shouldReturnAuthorByName() {
        assertThat(dao.findByName("Neil Gaiman").getId()).isEqualTo(1);
    }

    @Test
    @DisplayName(value = "возвращать из базы полный список авторов;")
    void shouldReturnFullListOfStoredAuthors() {
        assertThat(dao.findAll().size()).isGreaterThanOrEqualTo(5);
    }

    @Test
    @DisplayName(value = "возвращать из базы список авторов, привязанных к определенной книге;")
    void shouldReturnListOfAuthorsBoundToBook() {
        assertThat(dao.findAllByBookId(2).size()).isEqualTo(2);
    }

}