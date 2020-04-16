package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Question;
import ru.otus.homework.model.Session;
import ru.otus.homework.service.Printer;
import ru.otus.homework.service.TestProcessingService;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TestProcessingServiceFreeAnswersImpl implements TestProcessingService {

    private final Printer printer;

    @Override
    public void processTest(Session session, List<Question> questions) {

        if (questions.size() > 0) {
            printer.printLn("informational.testBeginning", null);
        }

        Scanner scanner = new Scanner(System.in);
        questions.forEach(question -> {
            printer.printLn("informational.questionNumber", new String[]{question.getId(), question.getQuestion()});
            String inputAnswer = scanner.nextLine();
            while (!Pattern.matches("[0-9]+", inputAnswer)) {
                printer.printLn("informational.incorrectInput.onlyNumbersWarning", null);
                printer.printLn("informational.questionNumber", new String[]{question.getId(), question.getQuestion()});
                inputAnswer = scanner.nextLine();
            }
            printer.printLn("informational.answerConfirmation", new String[]{inputAnswer});
            session.putAnswer(question, inputAnswer);
        });

    }
}
