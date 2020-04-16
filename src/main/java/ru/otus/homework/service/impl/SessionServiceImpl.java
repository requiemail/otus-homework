package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Session;
import ru.otus.homework.service.Printer;
import ru.otus.homework.service.QuestionsService;
import ru.otus.homework.service.ResultService;
import ru.otus.homework.service.SessionService;
import ru.otus.homework.service.TestProcessingService;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final QuestionsService questionsService;
    private final ResultService resultService;
    private final TestProcessingService processor;
    private final Printer printer;

    @Override
    public Session startSession() {
        Session session = new Session();
        Scanner scanner = new Scanner(System.in);
        printer.printLn("greeting.firstName", null);
        session.setUserFirstName(scanner.nextLine());
        printer.printLn("greeting.lastName", null);
        session.setUserLastName(scanner.nextLine());
        printer.printLn("greeting.mainGreeting", new String[]{session.userToString()});
        return session;

    }

    @Override
    public void processSession(Session session){

        processor.processTest(session, questionsService.getAll());

        if (session.getAnswers().size() > 0) {
            printer.printLn(
                    "informational.howMuchIsCorrect",
                    new Integer[]{resultService.calculateTestResult(session.getAnswers()), session.getAnswers().size()}
            );
        } else {
            printer.printLn("farewell.smthWentWrong", null);
        }
    }
}
