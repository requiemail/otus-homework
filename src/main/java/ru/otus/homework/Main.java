package ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.model.Question;
import ru.otus.homework.model.Session;
import ru.otus.homework.service.QuestionsService;
import ru.otus.homework.service.QuestionsServiceImpl;
import ru.otus.homework.service.ResultService;
import ru.otus.homework.service.ResultServiceImpl;
import ru.otus.homework.service.TestProcessingService;
import ru.otus.homework.service.TestProcessingServiceFreeAnswersImpl;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionsService questionsService = context.getBean(QuestionsServiceImpl.class);
        ResultService resultService = context.getBean(ResultServiceImpl.class);
        Session session = context.getBean(Session.class);
        TestProcessingService processor = context.getBean(TestProcessingServiceFreeAnswersImpl.class);

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
