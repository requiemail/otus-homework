package ru.otus.homework.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JoinDaoImpl.class)
@DisplayName("DAO джойнов должен:")
class JoinDaoImplTest {

    @Autowired
    private JoinDaoImpl dao;

    @Test
    @DisplayName("возвращать ID созданной связи книги и автора;")
    void shouldBindBookWithAuthors() {
        assertThat(dao.bookAuthorBindingCreate(1, 1)).isGreaterThanOrEqualTo(8);
    }

    @Test
    @DisplayName("возвращать ID созданной связи книги и жанра;")
    void bookGenreBindingCreate() {
        assertThat(dao.bookGenreBindingCreate(1, 1)).isGreaterThanOrEqualTo(12);
    }

    @Test
    @DisplayName("возвращать корректное количество удаленных связей;")
    void bookBindingsDelete() {
        assertThat(dao.bookBindingsDelete(2)).isEqualTo(5);
    }
}