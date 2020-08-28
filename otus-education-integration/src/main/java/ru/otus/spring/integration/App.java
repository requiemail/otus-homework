package ru.otus.spring.integration;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import ru.otus.spring.integration.domain.Student;
import ru.otus.spring.integration.domain.Specialist;
import ru.otus.spring.integration.gateway.Otus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@ComponentScan
@IntegrationComponentScan
@EnableIntegration
public class App {

    private static final String[] NAMES = {"Evgeniy", "Pavel", "Maria", "Irina", "Alexey", "Maksim", "Vlad"};

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        Otus otus = ctx.getBean(Otus.class);

        Collection<Student> students = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 20); i++) {
            students.add(Student.builder()
                    .name(NAMES[RandomUtils.nextInt(0, NAMES.length)])
                    .homeworkDoneCount(0)
                    .build());
        }

        System.out.println(String.format("Hello, new students! There are %d students in this course:", students.size()));
        System.out.println(students.stream()
                .map(Student::getName)
                .collect(Collectors.joining(",")));
        Collection<Specialist> specialists = otus.educationProcess(students);
        System.out.println("Congrats! Now all these students have solid knowledge: " + specialists.stream()
                .map(Specialist::getName)
                .collect(Collectors.joining(",")));
    }
}
