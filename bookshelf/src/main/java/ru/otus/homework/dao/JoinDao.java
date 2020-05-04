package ru.otus.homework.dao;

public interface JoinDao {

    long bookAuthorBindingCreate(long bookId, long authorId);
    long bookGenreBindingCreate(long bookId, long genreId);
    int bookBindingsDelete(long bookId);

}
