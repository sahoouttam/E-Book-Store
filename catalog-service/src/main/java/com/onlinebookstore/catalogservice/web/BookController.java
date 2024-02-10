package com.onlinebookstore.catalogservice.web;

import com.onlinebookstore.catalogservice.domain.Book;
import com.onlinebookstore.catalogservice.domain.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Iterable<Book> get() {
        log.info("Fetching the list of books in the catalog");
        return bookService.viewBookList();
    }

    @GetMapping("/{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
        log.info("Fetching the book with the ISBN {} from the catalog", isbn);
        return bookService.viewBookDetails(isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(@Valid  @RequestBody Book book) {
        log.info("Adding a new book to the catalog with ISBN {}", book.isbn());
        return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIsbn(@PathVariable String isbn) {
        log.info("Deleting book with ISBN {}", isbn);
        bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("/{isbn}")
    public Book put(@PathVariable String isbn, @Valid @RequestBody Book book) {
        log.info("updating book with ISBN {}", isbn);
        return bookService.editBookDetails(isbn, book);
    }
}
