package ru.otus.homework.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

@Configuration
public class AppConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        ms.setDefaultLocale(Locale.ENGLISH);
        return ms;
    }

    public static void main(String... args) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(stream);

        PrintStream originalPrintStream = System.out;

        System.setOut(ps);

        //it wil output to our stream
        System.out.println("printing something");

        //set it back
        System.setOut(originalPrintStream);

        System.out.println("-- retrieving output from stream --");
        String output = new String(stream.toByteArray());
        System.out.println("> " + output);
    }
}
