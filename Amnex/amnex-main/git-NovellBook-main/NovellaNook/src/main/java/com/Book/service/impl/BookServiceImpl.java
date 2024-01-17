package com.Book.service.impl;

import com.Book.entity.Book;
import com.Book.payload.BookDto;
import com.Book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<Book> getAllBooks(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<Book> books = bookPage.getContent();
        return books;
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
