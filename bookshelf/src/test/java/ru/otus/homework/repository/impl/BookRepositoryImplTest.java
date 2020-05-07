package ru.otus.homework.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;

import javax.persistence.TypedQuery;
import java.util.List;

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
        Book newBook = Book.builder().name("Test Book").build();
        em.persist(newBook);
        Comment newComment = Comment.builder()
                .commentAuthor("Test name")
                .commentText("Test Comment")
                .bookId(newBook.getId())
                .build();
        em.persist(newComment);
        assertThat(repository.findByIdWithComments(newBook.getId()).get()).isEqualTo(newBook);
    }
}