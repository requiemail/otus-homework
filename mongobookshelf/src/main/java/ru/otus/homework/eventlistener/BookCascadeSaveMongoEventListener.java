package ru.otus.homework.eventlistener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework.model.Book;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.GenreRepository;

@RequiredArgsConstructor
@Component
public class BookCascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if ((source instanceof Book) && (((Book) source).getAuthorList() != null)) {
            authorRepository.saveAll(((Book) source).getAuthorList());
        }
        if ((source instanceof Book) && (((Book) source).getGenreList() != null)) {
            genreRepository.saveAll(((Book) source).getGenreList());
        }
    }
}
