package ru.otus.homework.service.utils;

import ru.otus.homework.error.ResourceNotFoundException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class CsvAsBufferedReaderService {

    public static BufferedReader getBufferedReader(final String path) throws ResourceNotFoundException {
        Optional<InputStream> resourceAsStream = Optional.ofNullable(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
        InputStreamReader reader = new InputStreamReader(resourceAsStream.orElseThrow(() -> new ResourceNotFoundException(path)));
        return new BufferedReader(reader);
    }

}
