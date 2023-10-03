package com.onlinebookstore.orderservice.order.web;

import com.onlinebookstore.orderservice.order.domain.Order;
import com.onlinebookstore.orderservice.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class OrderJsonTests {

    @Autowired
    private JacksonTester<Order> json;

    @Test
    void testSerialize() throws Exception {
        Order order = new Order(394L, "1234567890", "Book Name", 9.90, 1,
                OrderStatus.ACCEPTED, Instant.now(), Instant.now(), "john", "marlen", 21);

        JsonContent<Order> jsonContent = json.write(order);

        assertThat(jsonContent).extractingJsonPathStringValue("@.id")
                .isEqualTo(order.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.bookIsbn")
                .isEqualTo(order.bookIsbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.bookName")
                .isEqualTo(order.bookName());
        assertThat(jsonContent).extractingJsonPathStringValue("@.bookPrice")
                .isEqualTo(order.bookPrice());
        assertThat(jsonContent).extractingJsonPathStringValue("@.quantity")
                .isEqualTo(order.quantity());
        assertThat(jsonContent).extractingJsonPathStringValue("@.status")
                .isEqualTo(order.status().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate")
                .isEqualTo(order.createdDate().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate")
                .isEqualTo(order.lastModifiedDate().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdBy")
                .isEqualTo(order.createdBy());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedBy")
                .isEqualTo(order.lastModifiedBy());
        assertThat(jsonContent).extractingJsonPathStringValue("@.version")
                .isEqualTo(order.version());

    }
}
