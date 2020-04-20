package ru.otus.homework.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class Book {

    private long id;
    private String name;
    private String isbnCode;
    private String publicationYear;
    private List<Author> authorList;
    private List<Genre> genreList;

    @Override
    public String toString() {
        return "Book id:" + id +
                ", name:'" + name + '\'' +
                ", ISBN: " + isbnCode +
                ", published in " + publicationYear +
                ", authors: " + authorList.stream().map(Author::toString).collect(Collectors.joining(", ")) +
                ", genres: " + genreList.stream().map(Genre::toString).collect(Collectors.joining(", "));
    }
}
