package ru.otus.homework.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Genre {

    @Id
    private String id;
    @Field
    private String name;


}