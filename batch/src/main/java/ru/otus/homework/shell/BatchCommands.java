package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job importUserJob;
    private final JobLauncher jobLauncher;

    @SneakyThrows
    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "start-migration")
    public void startMigrationJobWithJobLauncher() {
        JobExecution execution = jobLauncher.run(importUserJob, new JobParametersBuilder()
                .toJobParameters());
        System.out.println(execution);
    }

}
