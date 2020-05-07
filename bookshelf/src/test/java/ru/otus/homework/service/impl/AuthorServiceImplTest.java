package ru.otus.homework.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.error.NotFoundException;
import ru.otus.homework.model.Author;
import ru.otus.homework.repository.impl.AuthorRepositoryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис авторов должен:")
class AuthorServiceImplTest {

    @Mock
    private AuthorRepositoryImpl repository;

    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    @DisplayName("возвращать созданного автора;")
    void shouldReturnCreatedAuthor() {
        Author insertAuthor = Author.builder().name("Test Author").build();
        given(repository.save(insertAuthor)).willReturn(insertAuthor);

        assertThat(service.save(insertAuthor)).extracting("name")
                .isEqualTo("Test Author");
    }

    @Test
    @DisplayName("возвращать автора по ID;")
    void shouldReturnAuthorById() {
        Author insertAuthor = Author.builder().id(1L).name("Test Author").build();
        given(repository.findById(insertAuthor.getId())).willReturn(Optional.of(insertAuthor));
        assertThat(service.getById(1L)).isEqualTo(insertAuthor);
    }

    @Test
    @DisplayName("возвращать полный список авторов;")
    void shouldReturnAllAuthors() {
        List<Author> authors = Arrays.asList(
                Author.builder().id(1L).name("Test Author 1").build(),
                Author.builder().id(2L).name("Test Author 2").build(),
                Author.builder().id(3L).name("Test Author 3").build()
        );
        given(repository.findAll()).willReturn(authors);
        assertThat(service.getAll()).isEqualTo(authors);
    }

    @Test
    @DisplayName("выбрасывать корректное исключение;")
    void shouldThrowCorrectException() {
        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(1L)).isInstanceOf(NotFoundException.class).hasMessage("Author with id 1 not found");
    }

}