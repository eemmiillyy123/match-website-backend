package com.example.demo.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.Post;
import com.example.demo.model.User;

public class PostRowMapper implements RowMapper<Post>{
	@Override
	public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
		Post post=new Post();
		post.setArticleId(rs.getInt("article_id"));
		post.setBoardId(rs.getInt("board_id"));
		post.setContext(rs.getString("context"));
		post.setCreatedDate(rs.getDate("created_date"));
		post.setEmail(rs.getString("email"));
		post.setImg(rs.getString("img"));
		post.setShield(rs.getBoolean("shield"));
		post.setTitle(rs.getString("title"));
		return post;
	}
	
}