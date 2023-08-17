package com.onlinebookstore.catalogservice.web;

import com.onlinebookstore.catalogservice.domain.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    public void testSerialize() throws Exception {
        Instant now = Instant.now();
        Book book = new Book(394L, "1234567890", "Title", "Author", 9.90, now, now, 21);
        JsonContent<Book> jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathStringValue("@.id").isEqualTo(book.id());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathStringValue("@.price").isEqualTo(book.price());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate").isEqualTo(book.createdDate());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate").isEqualTo(book.lastModifiedDate());
        assertThat(jsonContent).extractingJsonPathStringValue("@.version").isEqualTo(book.version());
    }

    @Test
    public void testDeserialize() throws Exception {
        Instant instant = Instant.parse("2021-09-07T22:50:37.135029Z");
        String content = """
          {
            "id": 394,
            "isbn": "1234567890",
            "title": "Title",
            "author": "Author",
            "price": 9.90,
            "createdDate": "2021-09-07T22:50:37.135029Z",
            "lastModifiedDate": "2021-09-07T22:50:37.135029Z",
            "version": 21
          }
          """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book(394L, "1234567890", "Title", "Author", 9.90, instant, instant, 21));
    }
}
