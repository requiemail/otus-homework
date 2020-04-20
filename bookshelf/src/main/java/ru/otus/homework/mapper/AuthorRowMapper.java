package ru.otus.homework.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AuthorRowMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        return Author.builder()
                .id(resultSet.getLong("author_id"))
                .name(resultSet.getString("author_name"))
                .build();
    }
}
