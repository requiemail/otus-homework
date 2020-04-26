package ru.otus.homework.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.dao.JoinDao;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JoinDaoImpl implements JoinDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long bookAuthorBindingCreate(long bookId, long authorId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("book_id", bookId);
        params.addValue("author_id", authorId);
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO book_author_join (book_id, author_id) VALUES (:book_id, :author_id)",
                params,
                kh,
                new String[]{"book_author_join_id"});
        return kh.getKey().longValue();
    }

    @Override
    public long bookGenreBindingCreate(long bookId, long genreId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("book_id", bookId);
        params.addValue("genre_id", genreId);
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO book_genre_join (book_id, genre_id) VALUES (:book_id, :genre_id)",
                params,
                kh,
                new String[]{"book_genre_join_id"});
        return kh.getKey().longValue();
    }


    @Override
    public int bookBindingsDelete(long bookId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("book_id", bookId);
        return jdbc.update("DELETE FROM book_genre_join WHERE book_id = :book_id", params) +
                jdbc.update("DELETE FROM book_author_join WHERE book_id = :book_id", params);
    }

}
