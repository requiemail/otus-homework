package ru.otus.homework.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.mapper.GenreRowMapper;
import ru.otus.homework.model.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int insert(Genre genre) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("name", genre.getName());
        return jdbc.update("INSERT INTO genres (genre_name) VALUES (:name)", params);
    }

    @Override
    public Genre findById(long id){
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("SELECT genre_id, genre_name FROM genres WHERE genre_id = :id",
                params,
                new GenreRowMapper());
    }

    @Override
    public Genre findByName(String name) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        return jdbc.queryForObject("SELECT genre_id, genre_name FROM genres WHERE genre_name = :name",
                params,
                new GenreRowMapper());
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query("SELECT genre_id, genre_name FROM genres", new GenreRowMapper());
    }

    @Override
    public List<Genre> findAllByBookId(long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.query("SELECT g.genre_id, g.genre_name FROM book_genre_join AS bg JOIN genres AS g ON bg.genre_id = g.genre_id WHERE bg.book_id = :id",
                params,
                new GenreRowMapper());

    }

    @Override
    public int update(Genre genre) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        return jdbc.update("UPDATE authors SET author_name = :name WHERE author_id = :id", params);
   }

}
