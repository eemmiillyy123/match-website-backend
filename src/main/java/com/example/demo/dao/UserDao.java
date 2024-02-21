package com.example.demo.dao;

import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;

public interface UserDao {

	User getUserByEmail(String email);

	Integer createUser(UserRegisterRequest userRegisterRequest);

	User getUserById(Integer userId);


}
