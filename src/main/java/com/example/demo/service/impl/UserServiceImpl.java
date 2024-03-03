package com.example.demo.service.impl;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final static Logger log=LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired 
	private UserDao userDao;

	@Override
	public Integer register(UserRegisterRequest userRegisterRequest) {
		//檢查email是否存在
		User user=userDao.getUserByEmail(userRegisterRequest.getEmail());
		if(user!=null) {
			log.info("此email: {}已被註冊", userRegisterRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		else {
			//使用MD5生成密碼的雜湊值
			String hashedPassword=DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
			//字串轉成byte類型
			userRegisterRequest.setPassword(hashedPassword);
			log.info("註冊成功", userRegisterRequest.getEmail());
		}
		
		return userDao.createUser(userRegisterRequest);
	}

	@Override
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public User login(@Valid UserLoginRequest userLoginRequest) {
		User user= userDao.getUserByEmail(userLoginRequest.getEmail());
		if(user==null) {
			log.warn("此email: {}尚未註冊!",userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			
		}
		//使用MD5生成密碼的雜湊值
		String hashedPassword=DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
		if(user.getPassword().equals(hashedPassword)) {
			log.info("登入成功!");
			return user;
			
		}
		else{
			log.warn("此email: {}的密碼不正確!",userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
	}
}
