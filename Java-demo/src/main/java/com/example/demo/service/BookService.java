package com.example.demo.service;

import com.example.demo.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    List<Book> getList(int personId);

    int addBook(Book book);

    int updateBook(Book book);

    int delectBook(int id);

    int delBooks(List<Long> id);
}
