package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Genre save(Genre genre);
    Optional<Genre> findById(long id);
    List<Genre> findAll();

}
