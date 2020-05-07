package ru.otus.homework.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.error.NotFoundException;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.impl.GenreRepositoryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис жанров должен:")
class GenreServiceImplTest {

    @Mock
    private GenreRepositoryImpl repository;

    @InjectMocks
    private GenreServiceImpl service;

    @Test
    @DisplayName("возвращать созданный жанр;")
    void shouldReturnCreatedGenre() {
        Genre insertGenre = Genre.builder().name("Test Genre").build();
        given(repository.save(insertGenre)).willReturn(insertGenre);

        assertThat(service.save(insertGenre)).extracting("name")
                .isEqualTo("Test Genre");
    }

    @Test
    @DisplayName("возвращать жанр по ID;")
    void shouldReturnGenreById() {
        Genre insertGenre = Genre.builder().id(1L).name("Test Genre").build();
        given(repository.findById(insertGenre.getId())).willReturn(Optional.of(insertGenre));
        assertThat(service.getById(1L)).isEqualTo(insertGenre);
    }

    @Test
    @DisplayName("возвращать полный список жанров;")
    void shouldReturnAllGenres() {
        List<Genre> genres = Arrays.asList(
                Genre.builder().id(1L).name("Test Genre 1").build(),
                Genre.builder().id(2L).name("Test Genre 2").build(),
                Genre.builder().id(3L).name("Test Genre 3").build()
        );
        given(repository.findAll()).willReturn(genres);
        assertThat(service.getAll()).isEqualTo(genres);
    }

    @Test
    @DisplayName("выбрасывать корректное исключение;")
    void shouldThrowCorrectException() {
        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(1L)).isInstanceOf(NotFoundException.class).hasMessage("Genre with id 1 not found");
    }

}