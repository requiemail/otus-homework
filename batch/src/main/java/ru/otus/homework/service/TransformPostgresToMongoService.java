package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.model.mongo.Author;
import ru.otus.homework.model.mongo.Book;
import ru.otus.homework.model.mongo.Genre;
import ru.otus.homework.model.postgres.PostgresAuthor;
import ru.otus.homework.model.postgres.PostgresBook;
import ru.otus.homework.model.postgres.PostgresGenre;

import java.util.stream.Collectors;

@Service
public class TransformPostgresToMongoService implements TransformService {

    @Override
    public Book transform(PostgresBook book) {
        return Book.builder()
                .name(book.getName())
                .isbnCode(book.getIsbnCode())
                .publicationYear(book.getPublicationYear())
                .authorList(book.getAuthorList().stream().map(this::transformAuthor).collect(Collectors.toSet()))
                .genreList(book.getGenreList().stream().map(this::transformGenre).collect(Collectors.toSet()))
                .build();
    }

    private Author transformAuthor(PostgresAuthor author){
        return Author.builder().name(author.getName()).build();
    }

    private Genre transformGenre(PostgresGenre genre){
        return Genre.builder().name(genre.getName()).build();
    }
}
