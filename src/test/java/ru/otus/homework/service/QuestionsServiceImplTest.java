package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.model.Question;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
@DisplayName("QuestionService должен")
class QuestionsServiceImplTest {

    @Mock
    private QuestionsDao dao;
    @InjectMocks
    private QuestionsServiceImpl service;

    @Test
    @DisplayName("возвращать список из двух объектов Question")
    void getAll() {
        List<Question> questions = Arrays.asList(
                Question.builder().question("2+2").answer("4").id("1").build(),
                Question.builder().question("3+3").answer("6").id("2").build());
        given(dao.findAll()).willReturn(questions);

        assertThat(service.getAll()).isEqualTo(questions);
    }
}