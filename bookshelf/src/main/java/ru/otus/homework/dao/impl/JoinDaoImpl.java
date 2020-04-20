package ru.otus.homework.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.dao.JoinDao;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JoinDaoImpl implements JoinDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int bookAuthorBindingCreate(long bookId, long authorId) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("book_id", bookId);
        params.put("author_id", authorId);
        return jdbc.update("INSERT INTO book_author_join (book_id, author_id) VALUES (:book_id, :author_id)", params);
    }

    @Override
    public int bookGenreBindingCreate(long bookId, long genreId) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("book_id", bookId);
        params.put("genre_id", genreId);
        return jdbc.update("INSERT INTO book_genre_join (book_id, genre_id) VALUES (:book_id, :genre_id)", params);
    }


    @Override
    public int bookBindingsDelete(long bookId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("book_id", bookId);
        return jdbc.update("DELETE FROM book_genre_join WHERE book_id = :book_id", params) +
                jdbc.update("DELETE FROM book_author_join WHERE book_id = :book_id", params);
    }


}
