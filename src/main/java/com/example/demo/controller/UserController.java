package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
public class UserController {
	@Autowired 
	private UserService userService;
	
	@PostMapping("/user/register")
	public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
		//去判斷是否有註冊過，沒有就去創建一個帳號並回傳userId，有就去跳錯並在前端顯式此帳號已註冊過
		Integer userId=userService.register(userRegisterRequest);
		//用userId去查整筆註冊資料回來顯式在response
		User user=userService.getUserById(userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
		User user=userService.login(userLoginRequest);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}
