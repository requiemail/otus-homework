package ru.otus.homework.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.model.postgres.PostgresBook;

import java.util.List;
import java.util.Optional;

public interface PostgresBookRepository extends JpaRepository<PostgresBook, Long> {

    PostgresBook save(PostgresBook book);
    Optional<PostgresBook> findById(long id);
    List<PostgresBook> findAll();
    void delete(PostgresBook book);

}
