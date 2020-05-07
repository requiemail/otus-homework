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

@DisplayName("Репозиторий комментариев должен:")
@DataJpaTest
@Import(CommentRepositoryImpl.class)
class CommentRepositoryImplTest {

    @Autowired
    private CommentRepositoryImpl repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать комментарий после сохранения;")
    @Test
    void shouldReturnCommentAfterSave() {
        Comment newComment = Comment.builder()
                .commentAuthor("Test name")
                .commentText("Test Comment")
                .bookId((Long) em.persistAndGetId(Book.builder().name("Test book").build()))
                .build();
        assertThat(repository.save(newComment)).extracting("commentText", "commentAuthor")
                .contains("Test Comment", "Test name");
    }

    @DisplayName("возвращать комментарий по ID;")
    @Test
    void shouldReturnCommentById() {
        Comment newComment = Comment.builder()
                .commentAuthor("Test name")
                .commentText("Test Comment")
                .bookId((Long) em.persistAndGetId(Book.builder().name("Test book").build()))
                .build();
        Comment persistedComment = em.persist(newComment);
        assertThat(repository.findById(persistedComment.getId()).get()).isEqualTo(persistedComment);
    }

    @DisplayName("возвращать список всех комментариев;")
    @Test
    void shouldReturnAllComments() {
        TypedQuery<Comment> query = em.getEntityManager().createQuery("from Comment", Comment.class);
        List<Comment> actualComments = query.getResultList();
        assertThat(repository.findAll()).containsExactlyElementsOf(actualComments);
    }

    @DisplayName("возвращать список всех комментариев к определенной книге;")
    @Test
    void shouldReturnAllCommentsByBookId() {
        TypedQuery<Comment> query = em.getEntityManager().createQuery("from Comment", Comment.class);
        List<Comment> actualComments = query.getResultList();
        long idOfBookWithExistingComments = actualComments.stream()
                .findFirst()
                .orElse(Comment.builder().bookId(0).build())
                .getId();

        TypedQuery<Comment> queryByBookId = em.getEntityManager().createQuery("from Comment where book_id = :id", Comment.class);
        queryByBookId.setParameter("id", idOfBookWithExistingComments);

        List<Comment> actualCommentsByBookId = queryByBookId.getResultList();
        System.out.println(actualCommentsByBookId);
        assertThat(repository.findAllByBookId(1L)).containsExactlyElementsOf(actualCommentsByBookId);
    }
}