package com.Book.service.impl;

import com.Book.entity.Book;
import com.Book.payload.BookDto;
import com.Book.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto createBook(BookDto bookDto) {
        Book book = mapToEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return mapToDto(savedBook);
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    public BookDto updateBook(BookDto bookDto, Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setName(bookDto.getName());
                    book.setAuthor(bookDto.getAuthor());
                    book.setPublishedYear(bookDto.getPublishedYear());
                    Book updatedBook = bookRepository.save(book);
                    return mapToDto(updatedBook);
                })
                .orElse(null);
    }

    public boolean deleteBookById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private Book mapToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setPublishedYear(bookDto.getPublishedYear());
        return book;
    }

    private BookDto mapToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPublishedYear(book.getPublishedYear());
        return bookDto;
    }
}
