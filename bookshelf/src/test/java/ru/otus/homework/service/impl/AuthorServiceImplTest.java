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
    
    public static final long FIRST_ID = 1L;
    public static final long SECOND_ID = 2L;
    public static final long THIRD_ID = 3L;

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
        Author insertAuthor = Author.builder().id(FIRST_ID).name("Test Author").build();
        given(repository.findById(insertAuthor.getId())).willReturn(Optional.of(insertAuthor));
        assertThat(service.getById(FIRST_ID)).isEqualTo(insertAuthor);
    }

    @Test
    @DisplayName("возвращать полный список авторов;")
    void shouldReturnAllAuthors() {
        List<Author> authors = Arrays.asList(
                Author.builder().id(FIRST_ID).name("Test Author 1").build(),
                Author.builder().id(SECOND_ID).name("Test Author 2").build(),
                Author.builder().id(THIRD_ID).name("Test Author 3").build()
        );
        given(repository.findAll()).willReturn(authors);
        assertThat(service.getAll()).isEqualTo(authors);
    }

    @Test
    @DisplayName("выбрасывать корректное исключение;")
    void shouldThrowCorrectException() {
        given(repository.findById(FIRST_ID)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(FIRST_ID)).isInstanceOf(NotFoundException.class).hasMessage("Author with id 1 not found");
    }

}