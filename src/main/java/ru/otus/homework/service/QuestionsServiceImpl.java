package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.model.Question;

import java.util.List;

@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionsDao dao;

    public List<Question> getAll() {
        return dao.findAll();
    }

}
