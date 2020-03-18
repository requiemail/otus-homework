package ru.otus.homework.error;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String message) {
        super(message);
        System.out.println("!!! Requested questions not found! !!!");
    }
}
