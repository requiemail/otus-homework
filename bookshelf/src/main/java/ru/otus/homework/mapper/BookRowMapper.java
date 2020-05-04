package ru.otus.homework.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Book.builder()
                .id(resultSet.getLong("book_id"))
                .name(resultSet.getString("book_name"))
                .isbnCode(resultSet.getString("isbn"))
                .publicationYear(resultSet.getString("publication_year"))
                .build();
    }
}
