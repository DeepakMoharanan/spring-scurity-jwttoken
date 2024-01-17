package com.Book.controller;

import com.Book.entity.Book;
import com.Book.payload.BookDto;
import com.Book.service.impl.BookServiceImpl; // Add import statement for BookServiceImpl
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book")
public class BookController {
    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    // http://localhost:8080/api/book
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto createdBook = bookService.createBook(bookDto); // Fixed typo in method name
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // Get All Books
    // http://localhost:8080/api/book
    // http://localhost:8080/api/books?pageNo=0&pageSize=5&sortBy=id&sortDir=asc
    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return bookService.getAllBooks(pageNo, pageSize, sortBy, sortDir);
    }

    // http://localhost:8080/api/book/id/1
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getBookById(@PathVariable(name = "id") Long id) {
        BookDto foundBook = bookService.getBookById(id);
        if (foundBook != null) {
            return new ResponseEntity<>(foundBook, HttpStatus.OK);
        } else {
            String errorMessage = "Book not found with ID: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    // http://localhost:8080/api/book/id/1
    @PutMapping("/id/{id}") // Corrected path variable placement and added correct return type
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto, @PathVariable(name = "id") Long id) {
        BookDto bookResponse = bookService.updateBook(bookDto, id);
        if (bookResponse != null) {
            return new ResponseEntity<>(bookResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not available", HttpStatus.NOT_FOUND);
        }
    }

    // http://localhost:8080/api/book/id/1
    @DeleteMapping("/id/{id}") // Corrected path variable placement
    public ResponseEntity<?> deleteBookById(@PathVariable(name = "id") Long id) {
        boolean isDeleted = bookService.deleteBookById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not available", HttpStatus.NOT_FOUND);
        }
    }
}
