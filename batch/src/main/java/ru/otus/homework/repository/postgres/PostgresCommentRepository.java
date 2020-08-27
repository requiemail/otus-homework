package ru.otus.homework.repository.postgres;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.model.postgres.PostgresComment;

import java.util.List;
import java.util.Optional;

public interface PostgresCommentRepository extends CrudRepository<PostgresComment, Long> {

    Optional<PostgresComment> findById(long id);
    List<PostgresComment> findAll();

}
