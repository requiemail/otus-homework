package ru.otus.homework.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDaoImpl.class)
@DisplayName("DAO жанров должен:")
class GenreDaoImplTest {

    @Autowired
    private GenreDaoImpl dao;

    @Test
    @DisplayName("возвращать из базы сохраненный жанр;")
    void shouldReturnInsertedGenre() {
        long insertedGenre = dao.insert(Genre.builder().name("Test Genre").build());
        assertThat(dao.findById(insertedGenre).getName()).isEqualTo("Test Genre");

    }

    @Test
    @DisplayName("возвращать из базы жанр по его ID;")
    void shouldReturnGenreById() {
        assertThat(dao.findById(1).getName()).isEqualTo("Fantasy");
    }

    @Test
    @DisplayName("возвращать из базы жанр по его имени;")
    void shouldReturnGenreByName() {
        assertThat(dao.findByName("Fantasy").getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("возвращать из базы полный список жанров;")
    void shouldReturnFullListOfStoredGenres() {
        assertThat(dao.findAll().size()).isGreaterThanOrEqualTo(7);
    }

    @Test
    @DisplayName("возвращать из базы список жанров, привязанных к определенной книге;")
    void shouldReturnListOfGenresBoundToBook() {
        assertThat(dao.findAllByBookId(4).size()).isEqualTo(4);
    }
}