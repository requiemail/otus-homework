package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.homework.error.ResourceNotFoundException;
import ru.otus.homework.model.Question;
import ru.otus.homework.service.utils.CsvAsBufferedReaderService;

import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

@Repository("QuestionDao")
public class QuestionsDaoCsvImpl implements QuestionsDao {

    private final String pathToFile;

    public QuestionsDaoCsvImpl(@Value("${csv.path}") String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public List<Question> findAll() {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = CsvAsBufferedReaderService.getBufferedReader(pathToFile)) {
            String question;
            String csvSplitBy = ";";
            while ((question = br.readLine()) != null) {
                String[] questionAsArray = question.split(csvSplitBy);
                questions.add(Question.builder()
                        .id(questionAsArray[0])
                        .question(questionAsArray[1])
                        .answer(questionAsArray[2])
                        .build());
            }
        } catch (IOException | ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return questions;
    }

}
