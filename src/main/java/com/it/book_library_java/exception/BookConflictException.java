package com.it.book_library_java.exception;

public class BookConflictException extends IllegalStateException {
    public BookConflictException() {
        super("book exists, please check book id");
    }
}
