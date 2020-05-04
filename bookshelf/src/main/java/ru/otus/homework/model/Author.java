package ru.otus.homework.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Author {

    private long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }

}
