package ru.otus.spring.integration.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Specialist {

    private final String name;
    private final Boolean hasСertificate;

}
