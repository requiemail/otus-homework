package ru.otus.homework.service;

import ru.otus.homework.model.Session;

public interface SessionService {

    Session startSession();
    void processSession(Session session);

}
