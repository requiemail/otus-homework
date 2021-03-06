package ru.otus.homework.model.postgres;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@ToString(exclude = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class PostgresComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column(name = "comment_text")
    private String commentText;
    @Column(name = "comment_author")
    private String commentAuthor;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private PostgresBook book;

}
