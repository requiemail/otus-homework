package ru.otus.homework.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.error.NotFoundException;
import ru.otus.homework.model.Comment;
import ru.otus.homework.repository.CommentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис комментариев должен:")
class CommentServiceImplTest {

    public static final Long FIRST_ID = 1L;
    public static final Long SECOND_ID = 2L;
    public static final Long THIRD_ID = 3L;

    @Mock
    private CommentRepository repository;

    @InjectMocks
    private CommentServiceImpl service;

    @Test
    @DisplayName("возвращать комментарий по ID;")
    void shouldReturnCommentById() {
        Comment insertComment = Comment.builder().id(FIRST_ID).commentText("Test Comment").build();
        given(repository.findById(insertComment.getId())).willReturn(Optional.of(insertComment));
        assertThat(service.getById(FIRST_ID)).isEqualTo(insertComment);
    }

    @Test
    @DisplayName("возвращать полный список комментариев;")
    void shouldReturnAllComments() {
        List<Comment> comments = Arrays.asList(
                Comment.builder().id(FIRST_ID).commentText("Test Comment 1").build(),
                Comment.builder().id(SECOND_ID).commentText("Test Comment 2").build(),
                Comment.builder().id(THIRD_ID).commentText("Test Comment 3").build()
        );
        given(repository.findAll()).willReturn(comments);
        assertThat(service.getAll()).isEqualTo(comments);
    }

    @Test
    @DisplayName("выбрасывать корректное исключение;")
    void shouldThrowCorrectException() {
        given(repository.findById(FIRST_ID)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(FIRST_ID)).isInstanceOf(NotFoundException.class).hasMessage("Comment with id 1 not found");
    }

}