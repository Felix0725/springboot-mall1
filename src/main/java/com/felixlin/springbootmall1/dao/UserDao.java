package com.felixlin.springbootmall1.dao;

import com.felixlin.springbootmall1.dto.UserRegisterRequest;
import com.felixlin.springbootmall1.model.User;

public interface UserDao {

    User getUserById(Integer userId);
    Integer createUser(UserRegisterRequest userRegisterRequest);
}
