package ru.otus.homework.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.mongo.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
