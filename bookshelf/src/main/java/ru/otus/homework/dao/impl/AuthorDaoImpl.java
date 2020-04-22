package ru.otus.homework.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.mapper.AuthorRowMapper;
import ru.otus.homework.model.Author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource("name", author.getName());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO authors (author_name) VALUES (:name)",
                params,
                kh,
                new String[]{"author_id"});
        return kh.getKey().longValue();
    }

    @Override
    public Author findById(long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("SELECT author_id, author_name FROM authors WHERE author_id = :id",
                params,
                new AuthorRowMapper());
    }

    @Override
    public Author findByName(String name) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        return jdbc.queryForObject("SELECT author_id, author_name FROM authors WHERE author_name = :name",
                params,
                new AuthorRowMapper());
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("SELECT author_id, author_name FROM authors", new AuthorRowMapper());
    }

    @Override
    public List<Author> findAllByBookId(long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.query("SELECT a.author_id, a.author_name FROM book_author_join AS ba JOIN authors AS a ON ba.author_id = a.author_id WHERE ba.book_id = :id",
                params,
                new AuthorRowMapper());
    }

    @Override
    public int update(Author author) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", author.getId());
        params.put("name", author.getName());
        return jdbc.update("UPDATE authors SET author_name = :name WHERE author_id = :id", params);
    }

}
