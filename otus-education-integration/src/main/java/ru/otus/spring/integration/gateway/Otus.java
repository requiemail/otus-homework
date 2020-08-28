package ru.otus.spring.integration.gateway;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.domain.Specialist;
import ru.otus.spring.integration.domain.Student;

import java.util.Collection;

@MessagingGateway
public interface Otus {

    @Gateway(requestChannel = "studentsChannel", replyChannel = "specialistsChannel")
    Collection<Specialist> educationProcess(Collection<Student> students);

}
