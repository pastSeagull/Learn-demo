package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> queryAll();

    int delUser(String name);

    int addUser(User user);

    int updateUser(User user);

    int deletebooks(List id);
}
