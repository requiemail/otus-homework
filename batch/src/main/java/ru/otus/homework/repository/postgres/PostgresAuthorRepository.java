package ru.otus.homework.repository.postgres;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.model.postgres.PostgresAuthor;

import java.util.List;
import java.util.Optional;

public interface PostgresAuthorRepository extends CrudRepository<PostgresAuthor, Long> {

    PostgresAuthor save(PostgresAuthor author);
    Optional<PostgresAuthor> findById(long id);
    List<PostgresAuthor> findAll();

}
