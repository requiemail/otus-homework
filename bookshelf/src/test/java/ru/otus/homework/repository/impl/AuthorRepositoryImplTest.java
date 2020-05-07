package ru.otus.homework.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Author;

import javax.persistence.TypedQuery;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий авторов должен:")
@DataJpaTest
@Import(AuthorRepositoryImpl.class)
class AuthorRepositoryImplTest {

    @Autowired
    private AuthorRepositoryImpl repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать автора после сохранения;")
    @Test
    void shouldReturnAuthorAfterSave() {
        Author insertAuthor = Author.builder().name("Test Author").build();
        assertThat(repository.save(insertAuthor)).extracting("name").isEqualTo("Test Author");
    }

    @DisplayName("возвращать автора по ID;")
    @Test
    void shouldReturnAuthorById() {
        Author newAuthor = Author.builder().name("Test Author").build();
        Author persistedAuthor = em.persist(newAuthor);
        assertThat(repository.findById(persistedAuthor.getId()).get()).isEqualTo(persistedAuthor);
    }

    @DisplayName("возвращать список всех авторов;")
    @Test
    void shouldReturnAllAuthors() {
        TypedQuery<Author> query = em.getEntityManager().createQuery("from Author", Author.class);
        List<Author> actualAuthors = query.getResultList();
        assertThat(repository.findAll()).containsExactlyElementsOf(actualAuthors);
     }
}