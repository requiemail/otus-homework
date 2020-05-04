package ru.otus.homework.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.JoinDao;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.GenreService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис книг должен:")
class BookServiceImplTest {

    @Mock
    private BookDao dao;
    @Mock
    private JoinDao joinDao;

    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;

    @InjectMocks
    private BookServiceImpl service;


    @Test
    @DisplayName("возвращать ID созданной книги;")
    void shouldReturnIdOfNewBook() {

        Book testBook = Book.builder()
                .name("Test Book")
                .authorList(new ArrayList<>())
                .genreList(new ArrayList<>())
                .build();
        given(dao.insert(testBook)).willReturn(1L);
        assertThat(service.add(testBook)).isEqualTo(1L);

    }

    @Test
    @DisplayName("возвращать книгу по её ID со всеми вложенными авторами и жанрами;")
    void getById() {
        List<Genre> genres = Arrays.asList(
                Genre.builder().id(1L).name("Test Genre 1").build(),
                Genre.builder().name("Test Genre 2").build(),
                Genre.builder().name("Test Genre 3").build()
        );
        List<Author> authors = Arrays.asList(
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

        given(dao.findById(1L)).willReturn(Book.builder()
                .id(1L)
                .name("Test Book")
                .isbnCode("111-1111-1111-11")
                .publicationYear("2020")
                .build());
        given(authorService.getAllByBookId(1L)).willReturn(authors);
        given(genreService.getAllByBookId(1L)).willReturn(genres);

        assertThat(service.getById(1L)).isEqualTo(testBook);

    }

    @Test
    @DisplayName("возвращать список сохраненных книг;")
    void getAll() {
        List<Book> testBooks = Arrays.asList(
                Book.builder().id(1L).name("Test Book 1").build(),
                Book.builder().id(2L).name("Test Book 2").build(),
                Book.builder().id(3L).name("Test Book 3").build()
        );
        given(dao.findAll()).willReturn(testBooks);

        assertThat(service.getAll()).isEqualTo(testBooks);
    }

    @Test
    @DisplayName("возвращать правильное количество сохраненных книг;")
    void update() {
        Book testBook = Book.builder()
                .id(1L)
                .name("Test Book")
                .authorList(new ArrayList<>())
                .genreList(new ArrayList<>())
                .build();
        given(dao.update(testBook)).willReturn(1);

        assertThat(service.update(testBook)).isEqualTo(1);

    }

    @Test
    @DisplayName("возвращать правильное количество удаленных книг;")
    void delete() {
        given(dao.deleteById(1L)).willReturn(1);

        assertThat(service.delete(1L)).isEqualTo(1);
    }
}