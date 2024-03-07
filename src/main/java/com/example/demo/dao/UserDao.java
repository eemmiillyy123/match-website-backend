package com.example.demo.dao;

import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.dto.UsernameRevisionRequest;
import com.example.demo.model.User;

public interface UserDao {

	User getUserByEmail(String email);

	Integer createUser(UserRegisterRequest userRegisterRequest);

	User getUserById(Integer userId);

	User reviseEmail(String email, String newEmail);

	User reviseUsername(UsernameRevisionRequest request);

	Boolean verifyPassword(String hashedPassword, String email);

	User revisePassword(String newPassword, String email);


}
