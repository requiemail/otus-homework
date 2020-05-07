package ru.otus.homework.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.error.NotFoundException;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.impl.BookRepositoryImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис книг должен:")
class BookServiceImplTest {

    @Mock
    private BookRepositoryImpl repository;

    @InjectMocks
    private BookServiceImpl service;

    @Test
    @DisplayName("возвращать созданную книгу;")
    void shouldReturnCreatedBook() {
        Book testBook = Book.builder()
                .name("Test Book")
                .authorList(new HashSet<>())
                .genreList(new HashSet<>())
                .build();
        given(repository.save(testBook)).willReturn(testBook);
        assertThat(service.save(testBook)).isEqualTo(testBook);
    }

    @Test
    @DisplayName("возвращать книгу по её ID со всеми вложенными авторами и жанрами;")
    void shouldReturnBookById() {
        Set<Genre> genres = Set.of(
                Genre.builder().id(1L).name("Test Genre 1").build(),
                Genre.builder().name("Test Genre 2").build(),
                Genre.builder().name("Test Genre 3").build()
        );
        Set<Author> authors = Set.of(
                Author.builder().id(1L).name("Test Author 1").build(),
                Author.builder().name("Test Author 2").build(),
                Author.builder().name("Test Author 3").build()
        );
        Book testBook = Book.builder()
                .id(1L)
                .name("Test Book")
                .isbnCode("111-1111-1111-11")
                .publicationYear("2020")
                .genreList(genres)
                .authorList(authors)
                .build();

        given(repository.findById(1L)).willReturn(Optional.of(testBook));
        assertThat(service.getById(1L)).isEqualTo(testBook);

    }

    @Test
    @DisplayName("возвращать книгу по её ID со всеми комментариями;")
    void shouldReturnBookByIdWithComments() {
        Book testBook = Book.builder()
                .id(1L)
                .name("Test Book")
                .isbnCode("111-1111-1111-11")
                .publicationYear("2020")
                .comments(Set.of(Comment.builder().id(1L).build()))
                .build();

        given(repository.findById(1L)).willReturn(Optional.of(testBook));
        assertThat(service.getById(1L)).isEqualTo(testBook);

    }

    @Test
    @DisplayName("возвращать список сохраненных книг;")
    void shouldReturnAllBooks() {
        List<Book> testBooks = Arrays.asList(
                Book.builder().id(1L).name("Test Book 1").build(),
                Book.builder().id(2L).name("Test Book 2").build(),
                Book.builder().id(3L).name("Test Book 3").build()
        );
        given(repository.findAll()).willReturn(testBooks);
        assertThat(service.getAll()).isEqualTo(testBooks);
    }

    @Test
    @DisplayName("выбрасывать корректное исключение;")
    void shouldThrowCorrectException() {
        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(1L)).isInstanceOf(NotFoundException.class).hasMessage("Book with id 1 not found");
    }
}