package ru.otus.homework.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.error.NotFoundException;
import ru.otus.homework.model.Comment;
import ru.otus.homework.repository.impl.CommentRepositoryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис комментариев должен:")
class CommentServiceImplTest {

    @Mock
    private CommentRepositoryImpl repository;

    @InjectMocks
    private CommentServiceImpl service;

    @Test
    @DisplayName("возвращать созданный комментарий;")
    void shouldReturnCreatedComment() {
        Comment insertComment = Comment.builder().commentAuthor("Test name").commentText("Test Comment").build();
        given(repository.save(insertComment)).willReturn(insertComment);

        assertThat(service.save(insertComment)).extracting("commentText", "commentAuthor")
                .contains("Test Comment", "Test name");
    }

    @Test
    @DisplayName("возвращать комментарий по ID;")
    void shouldReturnCommentById() {
        Comment insertComment = Comment.builder().id(1L).commentText("Test Comment").build();
        given(repository.findById(insertComment.getId())).willReturn(Optional.of(insertComment));
        assertThat(service.getById(1L)).isEqualTo(insertComment);
    }

    @Test
    @DisplayName("возвращать полный список комментариев;")
    void shouldReturnAllComments() {
        List<Comment> comments = Arrays.asList(
                Comment.builder().id(1L).commentText("Test Comment 1").build(),
                Comment.builder().id(2L).commentText("Test Comment 2").build(),
                Comment.builder().id(3L).commentText("Test Comment 3").build()
        );
        given(repository.findAll()).willReturn(comments);
        assertThat(service.getAll()).isEqualTo(comments);
    }

    @Test
    @DisplayName("возвращать полный список комментариев к определенной книге;")
    void shouldReturnCorrectCommentsByBookId() {
        List<Comment> comments = Arrays.asList(
                Comment.builder().id(1L).commentText("Test Comment 1").bookID(1L).build(),
                Comment.builder().id(2L).commentText("Test Comment 2").bookID(1L).build(),
                Comment.builder().id(3L).commentText("Test Comment 3").bookID(1L).build()
        );
        given(repository.findAllByBookId(1L)).willReturn(comments);
        assertThat(service.getAllByBookId(1L)).isEqualTo(comments);
    }

    @Test
    @DisplayName("выбрасывать корректное исключение;")
    void shouldThrowCorrectException() {
        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(1L)).isInstanceOf(NotFoundException.class).hasMessage("Comment with id 1 not found");
    }

}