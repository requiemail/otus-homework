package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.model.Question;

import java.util.List;

public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionsDao dao;

    public QuestionsServiceImpl(QuestionsDao questionDao) {
        this.dao = questionDao;
    }

    public List<Question> getAll() {
        return dao.findAll();
    }

}
