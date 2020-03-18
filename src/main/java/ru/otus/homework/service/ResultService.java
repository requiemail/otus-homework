package ru.otus.homework.service;

import ru.otus.homework.model.Question;

import java.util.Map;

public interface ResultService {

    int calculateTestResult(Map<Question, String> answers);

}
