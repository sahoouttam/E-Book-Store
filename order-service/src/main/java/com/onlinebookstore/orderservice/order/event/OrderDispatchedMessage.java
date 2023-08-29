package com.onlinebookstore.orderservice.order.event;

public record OrderDispatchedMessage(
        Long orderId
)
{}
