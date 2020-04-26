package ru.otus.homework.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.impl.GenreDaoImpl;
import ru.otus.homework.model.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис жанров должен:")
class GenreServiceImplTest {

    @Mock
    private GenreDaoImpl dao;

    @InjectMocks
    private GenreServiceImpl service;

    @Test
    @DisplayName("возвращать ID созданного жанра;")
    void shouldReturnInsertedGenreId() {
        Genre insertGenre = Genre.builder().name("Test Genre").build();
        given(dao.insert(insertGenre)).willReturn(1L);
        assertThat(service.add(insertGenre)).isEqualTo(1L);
    }

    @Test
    @DisplayName("возвращать жанр по ID;")
    void getById() {
        Genre insertGenre = Genre.builder().id(1L).name("Test Genre").build();
        given(dao.findById(insertGenre.getId())).willReturn(insertGenre);
        assertThat(service.getById(1L)).isEqualTo(insertGenre);
    }

    @Test
    @DisplayName("возвращать жанр по названию;")
    void getByName() {
        Genre insertGenre = Genre.builder().id(1L).name("Test Genre").build();
        given(dao.findByName(insertGenre.getName())).willReturn(insertGenre);
        assertThat(service.getByName("Test Genre")).isEqualTo(insertGenre);
    }

    @Test
    @DisplayName("возвращать полный список жанров;")
    void getAll() {
        List<Genre> genres = Arrays.asList(
                Genre.builder().id(1L).name("Test Genre 1").build(),
                Genre.builder().id(2L).name("Test Genre 2").build(),
                Genre.builder().id(3L).name("Test Genre 3").build()
        );
        given(dao.findAll()).willReturn(genres);
        assertThat(service.getAll()).isEqualTo(genres);
    }

    @Test
    @DisplayName("возвращать список жанров по ID книги;")
    void getAllByBookId() {
        List<Genre> genres = Arrays.asList(
                Genre.builder().id(1L).name("Test Genre 1").build(),
                Genre.builder().id(2L).name("Test Genre 2").build(),
                Genre.builder().id(3L).name("Test Genre 3").build()
        );
        given(dao.findAllByBookId(1L)).willReturn(genres);
        assertThat(service.getAllByBookId(1L)).isEqualTo(genres);
    }
}