package ru.otus.homework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.homework.model.mongo.Book;
import ru.otus.homework.model.postgres.PostgresBook;
import ru.otus.homework.repository.postgres.PostgresBookRepository;
import ru.otus.homework.service.TransformService;

import java.util.HashMap;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public ItemWriter<Book> writer(MongoOperations mongoOperations) {
        return new MongoItemWriterBuilder<Book>().template(mongoOperations).collection("book").build();
    }

    @Bean
    public ItemReader<PostgresBook> reader(PostgresBookRepository repository) {
        return new RepositoryItemReaderBuilder<PostgresBook>()
                .name("reader")
                .methodName("findAll")
                .repository(repository)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<PostgresBook, Book> processor(TransformService transformService) {
        return transformService::transform;
    }

    @Bean
    public Job importUserJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step postgresToMongoStep(StepBuilderFactory stepBuilderFactory, ItemReader<PostgresBook> postgresReader,
                      ItemWriter<Book> mongoWriter, ItemProcessor<PostgresBook, Book> transformEntityProcessor) {
        return stepBuilderFactory.get("postgresToMongoStep")
                .<PostgresBook, Book>chunk(3)
                .reader(postgresReader)
                .processor(transformEntityProcessor)
                .writer(mongoWriter)
                .build();
    }

}
