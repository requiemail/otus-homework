package ru.otus.homework.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("genre_id"))
                .name(resultSet.getString("genre_name"))
                .build();
    }
}
