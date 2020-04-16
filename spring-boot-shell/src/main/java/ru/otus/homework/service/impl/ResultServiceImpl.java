package ru.otus.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.homework.model.Question;
import ru.otus.homework.service.ResultService;

import java.util.Map;

@Service
public class ResultServiceImpl implements ResultService {

    public int calculateTestResult(Map<Question, String> answers) {
        return answers.entrySet()
                .stream()
                .map(entry -> entry.getKey().getAnswer().equals(entry.getValue()) ? 1 : 0)
                .reduce(Integer::sum)
                .orElse(0);
    }

}
