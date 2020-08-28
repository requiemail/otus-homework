package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.homework.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

}
