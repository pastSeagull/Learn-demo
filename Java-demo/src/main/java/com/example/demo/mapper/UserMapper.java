package com.example.demo.mapper;


import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper {
    User queryAll();
    int delUser(@Param("id") Long id);
}
