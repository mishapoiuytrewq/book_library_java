package com.it.book_library_java.exception;

import java.util.NoSuchElementException;

public class BookNoSuchElementException extends NoSuchElementException {
    public BookNoSuchElementException() {
        super("book not found, please check book id");
    }
}
