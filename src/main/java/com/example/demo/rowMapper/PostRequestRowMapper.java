package com.example.demo.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.dto.PostRequest;
import com.example.demo.model.Post;

public class PostRequestRowMapper implements RowMapper<PostRequest>{
	@Override
	public PostRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
		PostRequest postRequest=new PostRequest();
		postRequest.setBoard(rs.getString("board"));
		postRequest.setContext(rs.getString("context"));
		postRequest.setEmail(rs.getString("email"));
		postRequest.setImg(rs.getString("img"));
		postRequest.setTitle(rs.getString("title"));
		return postRequest;
	}
	
}
