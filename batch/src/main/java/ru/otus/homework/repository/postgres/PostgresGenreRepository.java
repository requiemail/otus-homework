package ru.otus.homework.repository.postgres;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.model.postgres.PostgresGenre;

import java.util.List;
import java.util.Optional;

public interface PostgresGenreRepository extends CrudRepository<PostgresGenre, Long> {

    PostgresGenre save(PostgresGenre genre);
    Optional<PostgresGenre> findById(long id);
    List<PostgresGenre> findAll();

}
