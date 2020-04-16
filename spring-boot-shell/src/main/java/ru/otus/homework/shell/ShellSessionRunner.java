package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.homework.model.Session;
import ru.otus.homework.service.SessionService;

import java.util.Locale;

@ShellComponent
@RequiredArgsConstructor
public class ShellSessionRunner {

    private final SessionService sessionService;
    private final MessageSource messageSource;
    private Session session;

    @ShellMethod(value = "Initialize test session", key = "start")
    private void startSession() {
        this.session = sessionService.startSession();
    }

    @ShellMethod(value = "Process test", key = "test")
    @ShellMethodAvailability(value = "isSessionAvailable")
    private void processSession() {
        sessionService.processSession(session);
    }

    private Availability isSessionAvailable() {
        return (session == null)
                ? Availability.unavailable(messageSource.getMessage("userUnavailable", null, Locale.getDefault()))
                : Availability.available();
    }

}