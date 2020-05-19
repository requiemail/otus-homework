package ru.otus.homework.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.BookRepositoryCustom;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий книг должен:")
@DataJpaTest
@Import(BookRepositoryCustomImpl.class)
class BookRepositoryCustomImplTest {

    @Autowired
    private BookRepositoryCustom repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать книгу по ID с комментариями;")
    @Test
    void shouldReturnBookByIdWithComments() {
        Comment newComment = Comment.builder()
                .commentAuthor("Test name")
                .commentText("Test Comment")
                .build();
        Book newBook = Book.builder()
                .name("Test Book")
                .isbnCode("111-1111-1111-11")
                .publicationYear("2020")
                .authorList(Set.of(Author.builder().id(1L).name("Test Author 1").build()))
                .genreList(Set.of(Genre.builder().id(1L).name("Test Genre 1").build()))
                .comment(newComment).build();
        em.persist(newBook);
        assertThat(repository.findByIdWithComments(newBook.getId()).get()).isEqualTo(newBook);
    }
}