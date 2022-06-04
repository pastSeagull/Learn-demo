package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService UserServices;

    @GetMapping("/all")
    public List<User> getAllUser() {
        List<User> user = UserServices.getList();
        return user;
    }

    @GetMapping("/del")
    public int delUser(@RequestParam String name) {
        return UserServices.deleteUserByName(name);
    }

    @PostMapping("/add")
    public int addUser(@RequestBody User user) {
        return UserServices.addUserInfo(user);
    }

    @PostMapping("/update")
    public int updateUser(@RequestBody User user) {
        return UserServices.updateUserInfo(user);
    }

}
