package ru.otus.homework.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(name, book.name) &&
                Objects.equals(isbnCode, book.isbnCode) &&
                Objects.equals(publicationYear, book.publicationYear) &&
                Objects.equals(authorList, book.authorList) &&
                Objects.equals(genreList, book.genreList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isbnCode, publicationYear, authorList, genreList);
    }
}
