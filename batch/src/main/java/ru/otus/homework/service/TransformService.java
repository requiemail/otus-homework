package ru.otus.homework.service;

import ru.otus.homework.model.mongo.Author;
import ru.otus.homework.model.mongo.Book;
import ru.otus.homework.model.mongo.Genre;
import ru.otus.homework.model.postgres.PostgresAuthor;
import ru.otus.homework.model.postgres.PostgresBook;
import ru.otus.homework.model.postgres.PostgresGenre;

public interface TransformService {

    Book transform(PostgresBook book);

}
