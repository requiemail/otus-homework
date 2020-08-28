package ru.otus.spring.integration.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Student {

    private final String name;
    private Integer homeworkDoneCount;
    private Boolean hasСertificate;


    public void homeworkDoneCountIncrease(int i){
        this.homeworkDoneCount = getHomeworkDoneCount()+i;
    }

}
