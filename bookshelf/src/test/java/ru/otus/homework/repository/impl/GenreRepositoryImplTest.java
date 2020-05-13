package ru.otus.homework.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Genre;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий жанров должен:")
@DataJpaTest
@Import(GenreRepositoryImpl.class)
class GenreRepositoryImplTest {

    @Autowired
    private GenreRepositoryImpl repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать жанр после сохранения;")
    @Test
    void shouldReturnGenreAfterSave() {
        Genre insertGenre = Genre.builder().name("Test Genre").build();
        assertThat(repository.save(insertGenre)).extracting("name").isEqualTo("Test Genre");
    }

    @DisplayName("возвращать жанр по ID;")
    @Test
    void shouldReturnGenreById() {
        Genre newGenre = Genre.builder().name("Test Genre").build();
        Genre persistedGenre = em.persist(newGenre);
        assertThat(repository.findById(persistedGenre.getId()).get()).isEqualTo(persistedGenre);
    }

    @DisplayName("возвращать список всех жанров;")
    @Test
    void shouldReturnAllGenres() {
        TypedQuery<Genre> query = em.getEntityManager().createQuery("from Genre", Genre.class);
        List<Genre> actualGenres = query.getResultList();
        assertThat(repository.findAll()).containsExactlyElementsOf(actualGenres);
    }

}