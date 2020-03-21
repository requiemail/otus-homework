package ru.otus.homework.error;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String message) {
        super(message + "!!! Requested questions not found! !!!");
    }
}
