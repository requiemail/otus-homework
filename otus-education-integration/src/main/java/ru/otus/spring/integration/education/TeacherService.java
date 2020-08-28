package ru.otus.spring.integration.education;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Specialist;
import ru.otus.spring.integration.domain.Student;

@Slf4j
@Service
public class TeacherService {

    public Specialist certificate(Student student) throws Exception {
        log.info(String.format("Here student %s becomes a specialist...", student.getName()));
        return Specialist.builder()
                .name(student.getName())
                .hasСertificate(student.getHomeworkDoneCount() >= 18)
                .build();
    }

}
