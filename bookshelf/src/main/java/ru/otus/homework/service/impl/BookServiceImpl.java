package ru.otus.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.JoinDao;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao dao;
    private final JoinDao joinDao;

    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Book add(Book book) {

        dao.insert(book);
        book.setId(dao.findByName(book.getName()).getId());

        return checkAuthorsAndGenresRelations(book);

    }

    @Override
    public Book getById(long id) {
        Book book = dao.findById(id);
        book.setAuthorList(authorService.getAllByBookId(id));
        book.setGenreList(genreService.getAllByBookId(id));
        return book;
    }

    @Override
    public List<Book> getAll() {
        return dao.findAll()
                .stream()
                .peek(book -> {
                    book.setAuthorList(authorService.getAllByBookId(book.getId()));
                    book.setGenreList(genreService.getAllByBookId(book.getId()));
                })
                .collect(Collectors.toList());
    }

    @Override
    public int update(Book book) {
        joinDao.bookBindingsDelete(book.getId());
        return dao.update(checkAuthorsAndGenresRelations(book));
    }

    @Override
    public int delete(long id) {
        joinDao.bookBindingsDelete(id);
        return dao.deleteById(id);
    }

    private Book checkAuthorsAndGenresRelations(Book book) {

        List<Author> resultAuthors = checkAuthorsAndCreateIfNew(book.getAuthorList());
        bindAuthorsToBook(book.getId(), resultAuthors);

        List<Genre> resultGenre = checkGenresAndCreateIfNew(book.getGenreList());
        bindGenresToBook(book.getId(), resultGenre);

        book.setAuthorList(authorService.getAllByBookId(book.getId()));
        book.setGenreList(genreService.getAllByBookId(book.getId()));

        return book;
    }

    private List<Author> checkAuthorsAndCreateIfNew(List<Author> authors) {
        return authors.stream()
                .map(author -> {
                    if (author.getId() == 0) {
                        authorService.add(author);
                        return authorService.getByName(author.getName());
                    } else {
                        return author;
                    }
                })
                .collect(Collectors.toList());
    }

    private List<Genre> checkGenresAndCreateIfNew(List<Genre> genres) {
        return genres.stream()
                .map(genre -> {
                    if (genre.getId() == 0) {
                        genreService.add(genre);
                        return genreService.getByName(genre.getName());
                    } else {
                        return genre;
                    }
                })
                .collect(Collectors.toList());
    }

    private void bindAuthorsToBook(long bookId, List<Author> authors) {
        authors.forEach(author -> joinDao.bookAuthorBindingCreate(bookId, author.getId()));
    }

    private void bindGenresToBook(long bookId, List<Genre> genres) {
        genres.forEach(genre -> joinDao.bookGenreBindingCreate(bookId, genre.getId()));
    }

}
