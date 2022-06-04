package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

public interface UserService {

    List<User> getList();

    int deleteUserByName(String names);

    int addUserInfo(User user);

    int updateUserInfo(User user);
}
