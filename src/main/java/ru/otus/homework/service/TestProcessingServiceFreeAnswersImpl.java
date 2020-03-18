package ru.otus.homework.service;

import ru.otus.homework.model.Question;
import ru.otus.homework.model.Session;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TestProcessingServiceFreeAnswersImpl implements TestProcessingService {

    @Override
    public void processTest(Session session, List<Question> questions) {

        if (questions.size() > 0) {
            System.out.println("Let's do a little test:");
        }
        Scanner scanner = new Scanner(System.in);
        questions.forEach(question -> {
            System.out.println("Question #" + question.getId() + " is: " + question.getQuestion());
            String inputAnswer = scanner.nextLine();
            while (!Pattern.matches("[0-9]+", inputAnswer)) {
                System.out.println("Your answer must contain only number in this test!");
                System.out.println("Question #" + question.getId() + " is: " + question.getQuestion());
                inputAnswer = scanner.nextLine();
            }
            System.out.println("Your answer: " + inputAnswer);
            session.putAnswer(question, inputAnswer);
        });

    }
}
