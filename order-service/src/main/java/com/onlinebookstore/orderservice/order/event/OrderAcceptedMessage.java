package com.onlinebookstore.orderservice.order.event;

public record OrderAcceptedMessage(
        Long orderId
) {}
