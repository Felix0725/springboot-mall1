package com.felixlin.springbootmall1.service.impl;

import com.felixlin.springbootmall1.dao.UserDao;
import com.felixlin.springbootmall1.dto.UserRegisterRequest;
import com.felixlin.springbootmall1.model.User;
import com.felixlin.springbootmall1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
