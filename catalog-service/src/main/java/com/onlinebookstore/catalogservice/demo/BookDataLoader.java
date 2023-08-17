package com.onlinebookstore.catalogservice.demo;

import com.onlinebookstore.catalogservice.domain.Book;
import com.onlinebookstore.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("testData")
public class BookDataLoader {

    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        bookRepository.deleteAll();
        Book book1 = Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90, "Bookstore");
        Book book2 = Book.of("1234567892", "Polar Journey", "Iorek Polarson", 12.90, "Bookstore");
        bookRepository.saveAll(List.of(book1, book2));
    }
}
