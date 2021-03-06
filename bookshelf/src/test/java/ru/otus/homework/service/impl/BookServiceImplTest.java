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
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.GenreService;

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

    public static final Long FIRST_ID = 1L;
    public static final Long SECOND_ID = 2L;
    public static final Long THIRD_ID = 3L;

    @Mock
    private BookRepository repository;
    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;

    @InjectMocks
    private BookServiceImpl service;

    @Test
    @DisplayName("возвращать созданную книгу;")
    void shouldReturnCreatedBook() {
        Book testBook = Book.builder()
                .name("Test Book")
                .authorList(Set.of(Author.builder().id(FIRST_ID).name("Test Author 1").build()))
                .genreList(Set.of(Genre.builder().id(FIRST_ID).name("Test Genre 1").build()))
                .build();
        given(repository.save(testBook)).willReturn(testBook);
        assertThat(service.save(testBook)).isEqualTo(testBook);
    }

    @Test
    @DisplayName("возвращать книгу по её ID со всеми вложенными авторами и жанрами;")
    void shouldReturnBookById() {
        Set<Genre> genres = Set.of(
                Genre.builder().id(FIRST_ID).name("Test Genre 1").build(),
                Genre.builder().name("Test Genre 2").build(),
                Genre.builder().name("Test Genre 3").build()
        );
        Set<Author> authors = Set.of(
                Author.builder().id(FIRST_ID).name("Test Author 1").build(),
                Author.builder().name("Test Author 2").build(),
                Author.builder().name("Test Author 3").build()
        );
        Book testBook = Book.builder()
                .id(FIRST_ID)
                .name("Test Book")
                .isbnCode("111-1111-1111-11")
                .publicationYear("2020")
                .genreList(genres)
                .authorList(authors)
                .build();

        given(repository.findById(FIRST_ID)).willReturn(Optional.of(testBook));
        assertThat(service.getById(FIRST_ID)).isEqualTo(testBook);

    }

    @Test
    @DisplayName("возвращать книгу по её ID со всеми комментариями;")
    void shouldReturnBookByIdWithComments() {
        Book testBook = Book.builder()
                .id(FIRST_ID)
                .name("Test Book")
                .isbnCode("111-1111-1111-11")
                .publicationYear("2020")
                .authorList(Set.of(Author.builder().id(FIRST_ID).name("Test Author 1").build()))
                .genreList(Set.of(Genre.builder().id(FIRST_ID).name("Test Genre 1").build()))
                .comments(Set.of(Comment.builder().id(FIRST_ID).build()))
                .build();

        given(repository.findById(FIRST_ID)).willReturn(Optional.of(testBook));
        assertThat(service.getById(FIRST_ID)).isEqualTo(testBook);

    }

    @Test
    @DisplayName("возвращать список сохраненных книг;")
    void shouldReturnAllBooks() {
        List<Book> testBooks = Arrays.asList(
                Book.builder().id(FIRST_ID).name("Test Book 1").build(),
                Book.builder().id(SECOND_ID).name("Test Book 2").build(),
                Book.builder().id(THIRD_ID).name("Test Book 3").build()
        );
        given(repository.findAll()).willReturn(testBooks);
        assertThat(service.getAll()).isEqualTo(testBooks);
    }

    @Test
    @DisplayName("выбрасывать корректное исключение;")
    void shouldThrowCorrectException() {
        given(repository.findById(FIRST_ID)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(FIRST_ID)).isInstanceOf(NotFoundException.class).hasMessage("Book with id 1 not found");
    }
}