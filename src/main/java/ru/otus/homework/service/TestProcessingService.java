package ru.otus.homework.service;

import ru.otus.homework.model.Question;
import ru.otus.homework.model.Session;

import java.util.List;

public interface TestProcessingService {

    void processTest(Session session, List<Question> questions);

}
