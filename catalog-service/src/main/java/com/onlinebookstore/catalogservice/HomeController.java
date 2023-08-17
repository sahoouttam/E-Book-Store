package com.onlinebookstore.catalogservice;

import com.onlinebookstore.catalogservice.config.StoreProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final StoreProperties storeProperties;

    public HomeController(StoreProperties storeProperties) {
        this.storeProperties = storeProperties;
    }

    @GetMapping("/")
    public String getGreeting() {
        return storeProperties.getGreeting();
    }
}
