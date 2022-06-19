package com.it.book_library_java.view;

import com.it.book_library_java.entity.BookEntity;
import com.it.book_library_java.service.BookLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class BookLibraryView {
    private final BookLibraryService bookLibraryService;

    @Autowired
    public BookLibraryView(BookLibraryService bookLibraryService) {
        this.bookLibraryService = bookLibraryService;
    }

    @GetMapping("/book/private")
    public List<BookEntity> privateBooks() {
        return bookLibraryService.privateBooks();
    }

    @GetMapping("/book/public")
    public List<BookEntity> publicBooks() {
        return bookLibraryService.publicBooks();
    }

    @GetMapping("/book/id/{id}")
    public BookEntity book(@PathVariable UUID id) {
        return bookLibraryService.book(id);
    }
}
