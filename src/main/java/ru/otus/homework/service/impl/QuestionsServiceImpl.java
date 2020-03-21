package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.model.Question;
import ru.otus.homework.service.QuestionsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionsDao dao;

    public List<Question> getAll() {
        return dao.findAll();
    }

}
