package com.example.demo.service;

import javax.validation.Valid;

import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.dto.UsernameRevisionRequest;
import com.example.demo.model.User;

public interface UserService {

	Integer register(UserRegisterRequest userRegisterRequest);

	User getUserById(Integer userId);

	User login(@Valid UserLoginRequest userLoginRequest);

	User reviseEmail(String oldEmail, String newEmail);

	User reviseUsername(UsernameRevisionRequest request);

	User getUserByEmail(String email);

	Boolean verifyPassword(String oldPassword, String email);

	User revisePassword(String newPassword, String email);

}
