package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.service.Printer;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocalizedMessagePrinter implements Printer {

    private final Locale defaultLocale = Locale.getDefault();
    private final MessageSource ms;

    @Override
    public void printLn(String msg, Object[] templateArgs) {
        System.out.println(ms.getMessage(msg, templateArgs, defaultLocale));
    }

}
