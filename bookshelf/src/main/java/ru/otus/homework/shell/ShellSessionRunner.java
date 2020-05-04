package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ShellComponent
@RequiredArgsConstructor
@ShellCommandGroup(value = "Main commands:")
public class ShellSessionRunner {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final InputReader inputReader;

    private static final List<String> YES_AND_NO_OPTIONS = Arrays.asList("Y", "N");

    @ShellMethod(value = "Input new book")
    private String create() {
        Book book = Book.builder()
                .name(inputReader.prompt("Enter book name"))
                .isbnCode(inputReader.prompt("Enter ISBN code"))
                .publicationYear(inputReader.prompt("Enter publication year"))
                .authorList(createAuthorList())
                .genreList(createGenreList())
                .build();
        return bookService.getById(bookService.add(book)).toString();
    }

    @ShellMethod(value = "Show stored books")
    private String books(@ShellOption(value = "Id of particular book to show", defaultValue = "-1") long id) {
        if (id > 0) {
            return bookService.getById(id).toString();
        } else {
            return bookService.getAll().stream().map(Book::toString).collect(Collectors.joining("\n"));
        }
    }

    @ShellMethod(value = "Show stored authors")
    private List<String> authors() {
        return authorService.getAll().stream().map(Author::toString).collect(Collectors.toList());
    }

    @ShellMethod(value = "Show stored genres")
    private List<String> genres() {
        return genreService.getAll().stream().map(Genre::toString).collect(Collectors.toList());
    }

    @ShellMethod(value = "Change something in stored book")
    private void update(@ShellOption(value = "Id of particular book") long id) {

        Book book = bookService.getById(id);
        String currentOption;

        currentOption = inputReader.promptWithOptions(String.format("Do we change this book?\n%s", book), "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(currentOption)) {
            book.setName(inputReader.prompt("Enter book name"));
        }

        currentOption = inputReader.promptWithOptions("Do we change the name?", "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(currentOption)) {
            book.setName(inputReader.prompt("Enter book name"));
        }

        currentOption = inputReader.promptWithOptions("Do we change the ISBN code?", "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(currentOption)) {
            book.setIsbnCode(inputReader.prompt("Enter ISBN code"));
        }

        currentOption = inputReader.promptWithOptions("Do we change the publication year?", "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(currentOption)) {
            book.setPublicationYear(inputReader.prompt("Enter publication year"));
        }

        currentOption = inputReader.promptWithOptions("Do we change authors?", "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(currentOption)) {
            book.setAuthorList(createAuthorList());
        }

        currentOption = inputReader.promptWithOptions("Do we change genres?", "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(currentOption)) {
            book.setGenreList(createGenreList());
        }

        currentOption = inputReader.promptWithOptions(String.format("Save?\n%s", book), "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(currentOption)) {
            bookService.update(book);
        }

    }


    @ShellMethod(value = "Delete book by id")
    private void delete(@ShellOption(value = "Id of particular book") long id) {
        Book book = bookService.getById(id);
        String lastWarn = inputReader.promptWithOptions(String.format("Do you really want to delete this book?\n%s", book), "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(lastWarn)) {
            bookService.delete(id);
        }
    }


    private List<Author> createAuthorList() {
        List<Author> allAuthors = authorService.getAll();
        List<Author> authors = new ArrayList<>();
        String currentOption;
        Map<Long, String> authorOptions;
        do {
            currentOption = inputReader.promptWithOptions("Would you like to pick one of stored authors?", "N", YES_AND_NO_OPTIONS);
            if ("Y".equals(currentOption)) {
                authorOptions = allAuthors.stream().collect(Collectors.toMap(Author::getId, Author::getName));
                Long input = inputReader.selectFromList("Author:",
                        "Please enter one of the [] values",
                        authorOptions);
                authors.add(allAuthors.stream()
                        .filter(author -> author.getId() == input)
                        .findFirst()
                        .get()
                );
            } else {
                authors.add(Author.builder()
                        .name(inputReader.prompt("Enter author's name"))
                        .build());
            }
            currentOption = inputReader.promptWithOptions("Would you like to input another one author?", "N", YES_AND_NO_OPTIONS);
        } while ("Y".equals(currentOption));
        return authors;
    }

    private List<Genre> createGenreList() {
        List<Genre> genres = new ArrayList<>();
        List<Genre> allGenres = genreService.getAll();
        String currentOption;
        Map<Long, String> genreOptions;
        do {
            currentOption = inputReader.promptWithOptions("Would you like to pick one of stored genres?", "N", YES_AND_NO_OPTIONS);
            if ("Y".equals(currentOption)) {
                genreOptions = allGenres.stream().collect(Collectors.toMap(Genre::getId, Genre::getName));
                Long input = inputReader.selectFromList("Genre:",
                        "Please enter one of the [] values",
                        genreOptions);
                genres.add(allGenres.stream()
                        .filter(genre -> genre.getId() == input)
                        .findFirst()
                        .get()
                );
            } else {
                String authorName = inputReader.prompt("Enter genre");
                genres.add(Genre.builder()
                        .name(authorName)
                        .build());
            }
            currentOption = inputReader.promptWithOptions("Would you like to input another one genre?", "N", YES_AND_NO_OPTIONS);
        } while ("Y".equals(currentOption));
        return genres;
    }

}