package com.it.book_library_java.service;

import com.it.book_library_java.entity.BookEntity;
import com.it.book_library_java.exception.BookConflictException;
import com.it.book_library_java.exception.BookNoSuchElementException;
import com.it.book_library_java.repository.BookRepository;
import com.it.book_library_java.repository.CatalogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookLibraryService {
    private final BookRepository bookRepository;
    private final CatalogRepository catalogRepository;

    public BookLibraryService(BookRepository bookRepository, CatalogRepository catalogRepository) {
        this.bookRepository = bookRepository;
        this.catalogRepository = catalogRepository;
    }

    public BookEntity book(UUID id) {
        Optional<BookEntity> book = catalogRepository.book(id);
        if (book.isEmpty()) {
            throw new BookNoSuchElementException();
        }
        return book.get();
    }

    public List<BookEntity> privateBooks() {
        return catalogRepository.privateBooks();
    }

    public List<BookEntity> publicBooks() {
        return catalogRepository.publicBooks();
    }

    @Transactional
    public void saveBook(BookEntity bookEntity) {
        if (bookRepository.bookExists(bookEntity.id())) {
            throw new BookConflictException();
        }
        bookRepository.saveBook(
                bookEntity.id(), bookEntity.title(),
                bookEntity.author(), bookEntity.releaseDate()
        );
        catalogRepository.saveBook(
                bookEntity.id(), bookEntity.open()
        );
    }

    @Transactional
    public void deleteBook(UUID id) {
        if (bookRepository.bookNotExists(id)) {
            throw new BookNoSuchElementException();
        }
        catalogRepository.deleteBook(id);
        bookRepository.deleteBook(id);
    }

    @Transactional
    public void changeCatalogAtBook(UUID id, Boolean open) {
        if (bookRepository.bookNotExists(id)) {
            throw new BookNoSuchElementException();
        }
        catalogRepository.changeCatalogAtBook(id, open);
    }
}
