package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import ru.otus.homework.model.Session;

import java.util.Scanner;

@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final QuestionsService questionsService;
    private final ResultService resultService;
    private final TestProcessingService processor;

    @Override
    public void startSession() {

        Session session = new Session();

        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your first name?");
        session.setUserFirstName(scanner.nextLine());
        System.out.println("What is your last name?");
        session.setUserLastName(scanner.nextLine());

        System.out.println("Hello, " + session.userToString() + "!");
        processor.processTest(session, questionsService.getAll());

        if (session.getAnswers().size() > 0) {
            System.out.println("You answered correctly "
                    + resultService.calculateTestResult(session.getAnswers())
                    + " out of "
                    + session.getAnswers().size()
                    + " questions!");
        } else {
            System.out.println("At least you haven't made single mistake!");
        }

    }
}
