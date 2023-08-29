package com.onlinebookstore.edgeservice.user;

import java.util.List;

public record User(
        String username,
        String firstname,
        String lastname,
        List<String> roles
){}
