package ru.otus.spring.integration.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.integration.domain.Specialist;
import ru.otus.spring.integration.domain.Student;

@Slf4j
@Configuration
public class IntegrationConfig {

    @Bean
    public QueueChannel studentsChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public PublishSubscribeChannel specialistsChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    //Через 60 секунд получит всех специалистов
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers
                .fixedDelay(60000)
                .get();
    }

    @Bean
    public IntegrationFlow otusFlow() {
//              Получаем список студентов
        return IntegrationFlows.from("studentsChannel")
//                Разбиваем по одному
                .split()
//                Определяем для каждого студента количество выполненных домашних работ
                .transform(Student.class, f -> {
                    log.info(String.format("Checking homework done by a student: %s", f.getName()));
                    f.homeworkDoneCountIncrease(RandomUtils.nextInt(0, 36));
                    return f;
                })
//                Заканчиваем курс и делаем из студентов специалистов, выдаем сертификат всем, кто выполнил 18 домашних заданий
                .handle("teacherService", "certificate")
//                Специалистов с сертификатом поздравляем
                .<Specialist, Boolean>route(
                        Specialist::getHasСertificate,
                        mapping -> mapping
                                .subFlowMapping(true, sf -> sf.channel("specialistsChannel"))
                                .subFlowMapping(false, sf -> sf.channel("nullChannel"))
                )
                .get();
    }

}
