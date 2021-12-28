package com.emyasa;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> retrieveBooks() {
        return bookRepository.findAll();
    }

}