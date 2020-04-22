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

    @ShellMethod(value = "Input new book")
    private void create() {
        Book book = Book.builder()
                .name(inputReader.prompt("Enter book name"))
                .isbnCode(inputReader.prompt("Enter ISBN code"))
                .publicationYear(inputReader.prompt("Enter publication year"))
                .authorList(createAuthorList())
                .genreList(createGenreList())
                .build();
        System.out.println(bookService.getById(bookService.add(book)));
    }

    @ShellMethod(value = "Show stored books")
    private void books(@ShellOption(value = "Id of particular book to show", defaultValue = "-1") long id) {
        if (id > 0) {
            System.out.println(bookService.getById(id));
        } else {
            bookService.getAll().forEach(book -> System.out.println(book.toString()));
        }
    }

    @ShellMethod(value = "Show stored authors")
    private void authors() {
        authorService.getAll().forEach(author -> System.out.println(author.toString()));
    }

    @ShellMethod(value = "Show stored genres")
    private void genres() {
        genreService.getAll().forEach(genre -> System.out.println(genre.toString()));
    }

    @ShellMethod(value = "Change something in stored book")
    private void update(@ShellOption(value = "Id of particular book") long id) {
        Book book = bookService.getById(id);
        System.out.println(book);
        String currentOption;

        currentOption = inputReader.promptWithOptions("Do we change the name?", "N", Arrays.asList("Y", "N"));
        if ("Y".equals(currentOption)) {
            book.setName(inputReader.prompt("Enter book name"));
        }

        currentOption = inputReader.promptWithOptions("Do we change the ISBN code?", "N", Arrays.asList("Y", "N"));
        if ("Y".equals(currentOption)) {
            book.setIsbnCode(inputReader.prompt("Enter ISBN code"));
        }

        currentOption = inputReader.promptWithOptions("Do we change the publication year?", "N", Arrays.asList("Y", "N"));
        if ("Y".equals(currentOption)) {
            book.setPublicationYear(inputReader.prompt("Enter publication year"));
        }

        currentOption = inputReader.promptWithOptions("Do we change authors?", "N", Arrays.asList("Y", "N"));
        if ("Y".equals(currentOption)) {
            book.setAuthorList(createAuthorList());
        }

        currentOption = inputReader.promptWithOptions("Do we change genres?", "N", Arrays.asList("Y", "N"));
        if ("Y".equals(currentOption)) {
            book.setGenreList(createGenreList());
        }

        System.out.println(book);
        currentOption = inputReader.promptWithOptions("Save?", "N", Arrays.asList("Y", "N"));
        if ("Y".equals(currentOption)) {
            bookService.update(book);
        }

    }


    @ShellMethod(value = "Delete book by id")
    private void delete(@ShellOption(value = "Id of particular book") long id) {
        System.out.println(bookService.getById(id));
        String lastWarn = inputReader.promptWithOptions(String.format("Do you really want to delete book with id %d", id), "N", Arrays.asList("Y", "N"));
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
            currentOption = inputReader.promptWithOptions("Would you like to pick one of stored authors?", "N", Arrays.asList("Y", "N"));
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
            currentOption = inputReader.promptWithOptions("Would you like to input another one author?", "N", Arrays.asList("Y", "N"));
        } while ("Y".equals(currentOption));
        return authors;
    }

    private List<Genre> createGenreList() {
        List<Genre> genres = new ArrayList<>();
        List<Genre> allGenres = genreService.getAll();
        String currentOption;
        Map<Long, String> genreOptions;
        do {
            currentOption = inputReader.promptWithOptions("Would you like to pick one of stored genres?", "N", Arrays.asList("Y", "N"));
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
            currentOption = inputReader.promptWithOptions("Would you like to input another one genre?", "N", Arrays.asList("Y", "N"));
        } while ("Y".equals(currentOption));
        return genres;
    }

}