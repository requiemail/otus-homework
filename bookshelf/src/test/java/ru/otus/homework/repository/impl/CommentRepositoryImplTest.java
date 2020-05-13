package ru.otus.homework.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    @DisplayName("возвращать комментарий по ID;")
    @Test
    void shouldReturnCommentById() {
        Comment newComment = Comment.builder()
                .commentAuthor("Test name")
                .commentText("Test Comment")
                .build();
        Book newBook = Book.builder().name("Test Book").comment(newComment).build();
        em.persist(newBook);
        long persistedCommentId = newBook.getComments().stream().findFirst().get().getId();
        assertThat(repository.findById(persistedCommentId).get()).isEqualTo(newComment);
    }

    @DisplayName("возвращать список всех комментариев;")
    @Test
    void shouldReturnAllComments() {
        TypedQuery<Comment> query = em.getEntityManager().createQuery("from Comment", Comment.class);
        List<Comment> actualComments = query.getResultList();
        assertThat(repository.findAll()).containsExactlyElementsOf(actualComments);
    }

}