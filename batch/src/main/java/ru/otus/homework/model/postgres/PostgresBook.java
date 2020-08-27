package ru.otus.homework.model.postgres;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Builder
@Data
@ToString(exclude = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class PostgresBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_name")
    private String name;

    @Column(name = "ISBN")
    private String isbnCode;

    @Column(name = "publication_year")
    private String publicationYear;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_author_join",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<PostgresAuthor> authorList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_genre_join",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<PostgresGenre> genreList;

    @Singular
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "book")
    @JsonManagedReference
    private List<PostgresComment> comments;

}
