package ru.otus.homework.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("LocalizedMessagePrinter должен")
class LocalizedMessagePrinterTest {

    @Mock
    private MessageSource ms;
    @InjectMocks
    private LocalizedMessagePrinter printer;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("выводить в System.out Some test message")
    void printLn() {

        //initial.msg=Some {0} message

        String[] templateArg = {"test"};
        String finalMessage = "Some test message\n";
        given(ms.getMessage("initial.msg", templateArg, Locale.getDefault()))
                .willReturn("Some test message");
        printer.printLn("initial.msg", templateArg);

        assertThat(finalMessage).isEqualTo(outContent.toString());
    }

}