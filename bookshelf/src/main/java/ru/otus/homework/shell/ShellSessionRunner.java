package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;
import ru.otus.homework.service.GenreService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@ShellComponent
@RequiredArgsConstructor
@ShellCommandGroup(value = "Main commands:")
public class ShellSessionRunner {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    private final InputReader inputReader;

    private static final List<String> YES_AND_NO_OPTIONS = Arrays.asList("Y", "N");

    @ShellMethod(value = "Input new book")
    private String create() {
        Book book = Book.builder()
                .name(inputReader.prompt("Enter book name"))
                .isbnCode(inputReader.prompt("Enter ISBN code"))
                .publicationYear(inputReader.prompt("Enter publication year"))
                .authorList(createAuthorSet())
                .genreList(createGenreSet())
                .build();
        return bookService.save(book).toString();
    }

    @ShellMethod(value = "Show stored books")
    private String books(@ShellOption(value = "Id of particular book to show", defaultValue = "-1") long id) {
        if (id > 0) {
            return bookService.getByIdWithComments(id).toStringWithComments();
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

    @ShellMethod(value = "Show comments")
    private String comments(@ShellOption(value = "Id of particular book to show comments for", defaultValue = "-1") long id) {
        if (id > 0) {
            return commentService.getAllByBookId(id).stream().map(Comment::toString).collect(Collectors.joining("\n"));
        } else {
            return commentService.getAll().stream().map(Comment::toString).collect(Collectors.joining("\n"));
        }
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
            book.setAuthorList(createAuthorSet());
        }

        currentOption = inputReader.promptWithOptions("Do we change genres?", "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(currentOption)) {
            book.setGenreList(createGenreSet());
        }

        currentOption = inputReader.promptWithOptions(String.format("Save?\n%s", book), "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(currentOption)) {
            bookService.save(book);
        }

    }

    @ShellMethod(value = "Delete book by id")
    private void delete(@ShellOption(value = "Id of particular book") long id) {
        Book book = bookService.getByIdWithComments(id);
        String lastWarn = inputReader.promptWithOptions(String.format("Do you really want to delete this book with its comments?\n%s", book.toStringWithComments()), "N", YES_AND_NO_OPTIONS);
        if ("Y".equals(lastWarn)) {
            bookService.delete(book);
        }
    }

    @ShellMethod(value = "Create comment", key = {"comment"})
    private String createComment() {

        List<Book> allBooks = bookService.getAll();
        Map<Long, String> bookOptions = allBooks.stream().collect(Collectors.toMap(Book::getId, Book::getName));
        Long bookId = inputReader.selectFromList("Books:",
                "Please enter one of the [] values",
                bookOptions);
        Comment.CommentBuilder commentBuilder = Comment.builder()
                .bookId(bookId);

        String commentAuthor = inputReader.prompt("What is your name?");
        String commentText;
        do {
            commentText = inputReader.prompt("Please, enter the comment");
        } while (commentText.equals(""));

        commentBuilder
                .commentAuthor(commentAuthor.equals("") ? "anonymous" : commentAuthor)
                .commentText(commentText);

        return commentService.save(commentBuilder.build()).toString();

    }

    private Set<Author> createAuthorSet() {
        List<Author> allAuthors = authorService.getAll();
        Set<Author> authors = new HashSet<>();
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

    private Set<Genre> createGenreSet() {
        List<Genre> allGenres = genreService.getAll();
        Set<Genre> genres = new HashSet<>();
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