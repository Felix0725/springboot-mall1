package com.felixlin.springbootmall1.service;

import com.felixlin.springbootmall1.dto.UserLoginRequest;
import com.felixlin.springbootmall1.dto.UserRegisterRequest;
import com.felixlin.springbootmall1.model.User;

public interface UserService {

     User getUserById(Integer userId);
     Integer register(UserRegisterRequest userRegisterRequest);

     User login(UserLoginRequest userLoginRequest);


}
