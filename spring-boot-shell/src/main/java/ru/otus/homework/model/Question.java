package ru.otus.homework.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Question {

    private final String id;
    private final String question;
    private final String answer;

}
