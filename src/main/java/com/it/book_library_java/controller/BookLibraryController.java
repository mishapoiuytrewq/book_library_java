package com.it.book_library_java.controller;

import com.it.book_library_java.entity.BookEntity;
import com.it.book_library_java.service.BookLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class BookLibraryController {
    private final BookLibraryService bookLibraryService;

    @Autowired
    public BookLibraryController(BookLibraryService bookLibraryService) {
        this.bookLibraryService = bookLibraryService;
    }

    @PutMapping("/book")
    public void saveBook(@RequestBody @Valid BookEntity bookEntity) {
        bookLibraryService.saveBook(bookEntity);
    }

    @PatchMapping("/book/id/{id}/open/{open}")
    public void changeCatalogAtBook(@PathVariable UUID id, @PathVariable Boolean open) {
        bookLibraryService.changeCatalogAtBook(id, open);
    }

    @DeleteMapping("/book/id/{id}")
    public void deleteBook(@PathVariable UUID id) {
        bookLibraryService.deleteBook(id);
    }
}
