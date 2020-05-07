package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        name = "books-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("comments"),
                @NamedAttributeNode("authorList"),
                @NamedAttributeNode("genreList")
        }
)
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    @Column(name = "book_name")
    private String name;

    @Column(name = "ISBN")
    private String isbnCode;

    @Column(name = "publication_year")
    private String publicationYear;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "book_author_join",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authorList;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "book_genre_join",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genreList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "book_id")
    private Set<Comment> comments;

    @Override
    public String toString() {
        return "Book id:" + id +
                ", name:'" + name + '\'' +
                ", ISBN: " + isbnCode +
                ", published in " + publicationYear +
                ", authors: " + authorList.stream().map(Author::toString).collect(Collectors.joining(", ")) +
                ", genres: " + genreList.stream().map(Genre::toString).collect(Collectors.joining(", "));
    }

    public String toStringWithComments() {
        return "Book id:" + id +
                ", name:'" + name + '\'' +
                ", ISBN: " + isbnCode +
                ", published in " + publicationYear +
                ", authors: " + authorList.stream().map(Author::toString).collect(Collectors.joining(", ")) +
                ", genres: " + genreList.stream().map(Genre::toString).collect(Collectors.joining(", ")) +
                "\nComments:\n" + comments.stream().map(Comment::toString).collect(Collectors.joining("\n"));
    }
}
