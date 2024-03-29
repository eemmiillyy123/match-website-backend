package com.example.demo.dao.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.dto.UsernameRevisionRequest;
import com.example.demo.model.User;
import com.example.demo.rowMapper.UserRowMapper;
@Component
public class UserDaoImpl implements UserDao{
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public User getUserByEmail(String email) {
		String sql="select user_id,email,password,created_date,last_modified_date,username "
				+"from user where email=:email";
		Map<String,Object> map=new HashMap<>();
		map.put("email", email );
		List <User> list=namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
		if(list.isEmpty()) {
			return null;
		}
		else {
			return list.get(0);
		}

	}

	@Override
	public Integer createUser(UserRegisterRequest userRegisterRequest) {
		String sql="insert into user(email,password,created_date,last_modified_date,username) values(:email,:password,:createdDate,:lastmodifiedDate,:username)";
		Map<String,Object> map=new HashMap<>();
		map.put("email", userRegisterRequest.getEmail() );
		map.put("password", userRegisterRequest.getPassword() );
		map.put("username", userRegisterRequest.getUsername() );
		Date date=new Date();
		map.put("createdDate", date );
		map.put("lastmodifiedDate", date );
		KeyHolder keyHolder=new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		int userId=keyHolder.getKey().intValue();
		return userId;
	}

	@Override
	public User getUserById(Integer userId) {
		String sql="select user_id,email,password,created_date,last_modified_date,username "
				+"from user where user_id=:userId";
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId );
		List <User> list=namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
		if(list.isEmpty()) {
			return null;
		}
		else {
			return list.get(0);
		}
	}

	@Override
	@Transactional
	public User reviseEmail(String email, String newEmail) {
		String sql="update user set email=:newEmail where email=:email ";
		Map<String,Object> map=new HashMap<>();
		map.put("email", email);
		map.put("newEmail", newEmail);
		namedParameterJdbcTemplate.update(sql, map);
		User user=getUserByEmail(newEmail);
		return user;
	}

	@Override
	public User reviseUsername(UsernameRevisionRequest request) {
		String username=request.getUsername();
		String email=request.getEmail();
		String sql="update user set username=:username where email=:email ";
		Map<String,Object> map=new HashMap<>();
		map.put("username", username);
		map.put("email", email);
		namedParameterJdbcTemplate.update(sql, map);
		User user=getUserByEmail(email);
		return user;
	}

	@Override
	public Boolean verifyPassword(String hashedPassword, String email) {
		User user=getUserByEmail(email);
		if(  hashedPassword.equals(user.getPassword())) {
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public User revisePassword(String hashedPassword, String email) {
		String sql="update user set password=:password  where email=:email ";
		Map<String,Object> map=new HashMap<>();
		map.put("password", hashedPassword);
		map.put("email", email);
		namedParameterJdbcTemplate.update(sql, map);
		User user=getUserByEmail(email);
		return user;
	}
}
