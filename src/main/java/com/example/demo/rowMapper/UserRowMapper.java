package com.example.demo.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.User;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user=new User();
		user.setCreatedDate(rs.getTimestamp("created_date"));
		user.setLastmodifiedDate(rs.getTimestamp("last_modified_date"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setUserId(rs.getInt("user_id"));
		user.setUsername(rs.getString("username"));
		return user;
	}
	

}
