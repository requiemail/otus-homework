package ru.otus.homework.dao;

public interface JoinDao {

    int bookAuthorBindingCreate(long bookId, long authorId);
    int bookGenreBindingCreate(long bookId, long genreId);
    int bookBindingsDelete(long bookId);

}
