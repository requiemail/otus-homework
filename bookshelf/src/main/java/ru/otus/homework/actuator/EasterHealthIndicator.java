package ru.otus.homework.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class EasterHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().withDetail("Easter egg", "Your egg is healthy!").build();
    }
}
