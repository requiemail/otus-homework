package ru.otus.homework.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.error.NotFoundException;
import ru.otus.homework.model.Comment;
import ru.otus.homework.repository.CommentRepository;
import ru.otus.homework.service.CommentService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public Comment getById(Long id) {
        sleepRandomly();
        return repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Comment with id %d not found", id)));
    }

    @Override
    @HystrixCommand(
            fallbackMethod = "getFallbackComment",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            })
    public List<Comment> getAll() {
        sleepRandomly();
        return repository.findAll();
    }

    public List<Comment> getFallbackComment() {
        return Collections.singletonList(Comment.builder()
                .commentAuthor("system")
                .commentText("***all comments temporary unavailable, try later***")
                .build());
    }

    private void sleepRandomly() {
        Random rand = new Random();
        int randomInt = rand.nextInt(3) + 1;
        if (randomInt == 3) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
