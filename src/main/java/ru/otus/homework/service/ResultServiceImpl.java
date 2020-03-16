package ru.otus.homework.service;

import ru.otus.homework.model.Question;

import java.util.Map;

public class ResultServiceImpl implements ResultService {

    public int calculateTestResult(Map<Question, String> answers) {
        return answers.entrySet()
                .stream()
                .map(entry -> entry.getKey().getAnswer().equals(entry.getValue()) ? 1 : 0)
                .reduce(Integer::sum)
                .orElse(0);
    }

}
