package com.example.demo.mapper;

import com.example.demo.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> BookList(int PersonId);

    int addBook(Book book);

    int updateBook(Book book);

    int delectBook(int id);

    int delBooks(List<Long> id);

}
