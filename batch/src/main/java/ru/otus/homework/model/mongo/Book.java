package ru.otus.homework.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Book {

    @Id
    private String id;

    @Field(name = "book_name")
    private String name;

    @Field(name = "ISBN")
    private String isbnCode;

    @Field(name = "publication_year")
    private String publicationYear;

    @DBRef
    private Set<Author> authorList;

    @DBRef
    private Set<Genre> genreList;

}
