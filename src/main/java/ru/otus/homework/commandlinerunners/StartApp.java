package ru.otus.homework.commandlinerunners;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.homework.service.SessionService;

@Component
@RequiredArgsConstructor
public class StartApp implements CommandLineRunner {

    private final SessionService sessionService;

    @Override
    public void run(String... args) {
        sessionService.startSession();
    }

}
