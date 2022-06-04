package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService BookServices;

    @GetMapping("/book")
    public List<Book> BookList(@RequestParam int personId) {
        return BookServices.getList(personId);
    }

    @PostMapping("/addbook")
    public int addBook(@RequestBody Book book) {
        return BookServices.addBook(book);
    }

    @PostMapping("/updatebook")
    public int updateBook(@RequestBody Book book) {
        return BookServices.updateBook(book);
    }

    @GetMapping("/delectbook")
    public int delectBook(@RequestParam int id) {
        return BookServices.delectBook(id);
    }

    @PostMapping("/api/delbooks")
    public int delBooks(@RequestBody List<Long> id) {
        return BookServices.delBooks(id);
    }

}
