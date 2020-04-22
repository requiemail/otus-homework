package ru.otus.homework.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.mapper.BookRowMapper;
import ru.otus.homework.model.Book;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("isbn", book.getIsbnCode());
        params.addValue("year", book.getPublicationYear());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO books (book_name, isbn, publication_year) VALUES (:name, :isbn, :year)",
                params,
                kh,
                new String[]{"book_id"});
        return kh.getKey().longValue();
    }

    @Override
    public Book findById(long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("SELECT book_id, book_name, isbn, publication_year FROM books WHERE book_id = :id",
                params,
                new BookRowMapper());
    }

    @Override
    public Book findByName(String name) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        return jdbc.queryForObject("SELECT book_id, book_name, isbn, publication_year FROM books WHERE book_name = :name",
                params,
                new BookRowMapper());
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT book_id, book_name, isbn, publication_year FROM books", new BookRowMapper());
    }

    @Override
    public int update(Book book) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("id", book.getId());
        params.put("name", book.getName());
        params.put("isbn", book.getIsbnCode());
        params.put("publication_year", book.getPublicationYear());
        return jdbc.update("UPDATE books SET book_name = :name, isbn = :isbn, publication_year = :publication_year WHERE book_id = :id", params);
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.update("DELETE FROM books WHERE book_id = :id", params);

    }
}
