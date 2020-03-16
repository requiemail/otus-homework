package ru.otus.homework.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@Data
public class Session {

    private String userFirstName;
    private String userLastName;
    private Map<Question, String> answers = new LinkedHashMap<>();

    public String userToString() {
        return userFirstName + " " + userLastName;
    }

    public void putAnswer(Question question, String answer) {
        answers.put(question, answer);
    }

}
