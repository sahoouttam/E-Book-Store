package com.onlinebookstore.catalogservice.domain;

import com.onlinebookstore.catalogservice.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    void findAllBooks() {
        Book book1 = Book.of("1234561234", "Title", "Author", 12.90, "Bookshop");
        Book book2 = Book.of("1234561234", "Another Title", "Author", 12.90, "Bookshop");
        jdbcAggregateTemplate.insert(book1);
        jdbcAggregateTemplate.insert(book2);

        Iterable<Book> actualBooks = bookRepository.findAll();

        assertThat(StreamSupport.stream(actualBooks.spliterator(), true)
                .filter(book -> book.isbn().equals(book1.isbn()) || book.isbn().equals(book2.isbn()))
                .collect(Collectors.toList())).hasSize(2);
    }

    @Test
    void findBookByIsbnWhenExisting() {
        String bookIsbn = "1234561237";
        Book book = Book.of(bookIsbn, "Tilte", "Author", 12.90, "Bookstore");
        jdbcAggregateTemplate.insert(book);
        Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);
        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().isbn()).isEqualTo(bookIsbn);
    }

    @Test
    void findBookByIsbnWhenNotExisting() {
        Optional<Book> actualBook = bookRepository.findByIsbn("1234561238");
        assertThat(actualBook).isEmpty();
    }

    @Test
    void existsByIsbnWhenExisting() {
        String bookIsbn = "1234561239";
        Book bookToCreate = Book.of(bookIsbn, "Title", "Author", 12.90, "Bookstore");
        jdbcAggregateTemplate.insert(bookToCreate);
        assertThat(bookRepository.existsByIsbn(bookIsbn)).isTrue();
    }

    @Test
    void existsByIsbnWhenNotExisting() {
        String bookIsbn = "1234512310";
        assertThat(bookRepository.existsByIsbn(bookIsbn)).isFalse();
    }

    @Test
    void deleteByIsbn() {
        String bookIsbn = "1234561241";
        Book bookToCreate = Book.of(bookIsbn, "Title", "Author", 12.90, "Bookstore");
        Book persistedBook = jdbcAggregateTemplate.insert(bookToCreate);

        bookRepository.deleteByIsbn(bookIsbn);

        assertThat(jdbcAggregateTemplate.findById(persistedBook.id(), Book.class)).isNull();
    }

}
