package com.example.demo.service.impl;

import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper BookMappers;

    @Override
    public List<Book> getList(int personId) {
        return BookMappers.BookList(personId);
    }

    @Override
    public int addBook(Book book) {
        return BookMappers.addBook(book);
    }

    @Override
    public int updateBook(Book book) { return BookMappers.updateBook(book);}

    @Override
    public int delectBook(int id) { return BookMappers.delectBook(id); }

    @Override public int delBooks(List<Long> id) { return BookMappers.delBooks(id);}
}
