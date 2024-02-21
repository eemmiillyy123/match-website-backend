package com.example.demo.service;

import javax.validation.Valid;

import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;

public interface UserService {

	Integer register(UserRegisterRequest userRegisterRequest);

	User getUserById(Integer userId);

	User login(@Valid UserLoginRequest userLoginRequest);

}
