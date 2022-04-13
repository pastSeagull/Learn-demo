package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserMapper UserMappers;

    @GetMapping("/getAllUser")
    public String getAllUser(Model model) {
        User user = UserMappers.queryAll();
        return "1";
    }
    @GetMapping("/delUser")
    public String delUser(Model model) {
        return "2";
    }
}
