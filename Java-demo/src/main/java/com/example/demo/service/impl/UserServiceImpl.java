package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数配置 服务层实现
 *
 *
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper UserMappers;

    @Override
    public List<User> getList() {
        return UserMappers.queryAll();
    }

    @Override
    public int deleteUserByName(String name) {
        return UserMappers.delUser(name);
    }

    @Override
    public int addUserInfo(User user) {
        return UserMappers.addUser(user);
    }

    @Override
    public int updateUserInfo(User user) {
        return UserMappers.updateUser(user);
    }


}
