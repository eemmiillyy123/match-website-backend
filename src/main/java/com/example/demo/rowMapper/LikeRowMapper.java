package com.example.demo.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.Like;

public class LikeRowMapper implements RowMapper<Like>{
		@Override
		public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
			Like like=new Like();
			like.setArticleId(rs.getInt("articleId"));
			like.setUserId(rs.getInt("userId"));
			return like;
		}
}
