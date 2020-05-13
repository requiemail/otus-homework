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

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий книг должен:")
@DataJpaTest
@Import(BookRepositoryImpl.class)
class BookRepositoryImplTest {

    @Autowired
    private BookRepositoryImpl repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать книгу после сохранения;")
    @Test
    void shouldReturnBookAfterSave() {
        Book newBook = Book.builder()
                .name("Test Book")
                .build();
        assertThat(repository.save(newBook)).extracting("name").isEqualTo("Test Book");
    }

    @DisplayName("возвращать книгу по ID;")
    @Test
    void shouldReturnBookById() {
        Book newBook = Book.builder().name("Test Book").build();
        Book persistedBook = em.persist(newBook);
        assertThat(repository.findById(persistedBook.getId()).get()).isEqualTo(persistedBook);
    }

    @DisplayName("возвращать список всех книг;")
    @Test
    void shouldReturnAllBooks() {
        TypedQuery<Book> query = em.getEntityManager().createQuery("from Book", Book.class);
        List<Book> actualBooks = query.getResultList();
        assertThat(repository.findAll()).containsExactlyElementsOf(actualBooks);
    }

    @DisplayName("корректно удалять книгу;")
    @Test
    void shouldDelete() {
        Book newBook = Book.builder().name("Test Book").build();
        Book persistedBook = em.persist(newBook);
        long persistedBookId = persistedBook.getId();
        repository.delete(persistedBook);
        assertThat(em.find(Book.class, persistedBookId)).isNull();
    }

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