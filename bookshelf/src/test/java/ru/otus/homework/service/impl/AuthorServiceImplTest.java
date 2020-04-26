package ru.otus.homework.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.impl.AuthorDaoImpl;
import ru.otus.homework.model.Author;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис авторов должен:")
class AuthorServiceImplTest {

    @Mock
    private AuthorDaoImpl dao;

    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    @DisplayName("возвращать ID созданного автора;")
    void shouldReturnInsertedAuthorId() {
        Author insertAuthor = Author.builder().name("Test Author").build();
        given(dao.insert(insertAuthor)).willReturn(1L);
        assertThat(service.add(insertAuthor)).isEqualTo(1L);
    }

    @Test
    @DisplayName("возвращать автора по ID;")
    void getById() {
        Author insertAuthor = Author.builder().id(1L).name("Test Author").build();
        given(dao.findById(insertAuthor.getId())).willReturn(insertAuthor);
        assertThat(service.getById(1L)).isEqualTo(insertAuthor);
    }

    @Test
    @DisplayName("возвращать автора по названию;")
    void getByName() {
        Author insertAuthor = Author.builder().id(1L).name("Test Author").build();
        given(dao.findByName(insertAuthor.getName())).willReturn(insertAuthor);
        assertThat(service.getByName("Test Author")).isEqualTo(insertAuthor);
    }

    @Test
    @DisplayName("возвращать полный список авторов;")
    void getAll() {
        List<Author> authors = Arrays.asList(
                Author.builder().id(1L).name("Test Author 1").build(),
                Author.builder().id(2L).name("Test Author 2").build(),
                Author.builder().id(3L).name("Test Author 3").build()
        );
        given(dao.findAll()).willReturn(authors);
        assertThat(service.getAll()).isEqualTo(authors);
    }

    @Test
    @DisplayName("возвращать список авторов по ID книги;")
    void getAllByBookId() {
        List<Author> genres = Arrays.asList(
                Author.builder().id(1L).name("Test Author 1").build(),
                Author.builder().id(2L).name("Test Author 2").build(),
                Author.builder().id(3L).name("Test Author 3").build()
        );
        given(dao.findAllByBookId(1L)).willReturn(genres);
        assertThat(service.getAllByBookId(1L)).isEqualTo(genres);
    }

}