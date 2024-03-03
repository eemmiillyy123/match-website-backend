package com.example.demo.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.dto.LikeRequest;
import com.example.demo.model.Article;

public class LikeRequestRowMapper  implements RowMapper<LikeRequest>{
	@Override
	public LikeRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
		LikeRequest likeRequest=new LikeRequest();
//		article.setBoard(rs.getString("board"));
		likeRequest.setArticleId(null);
//		Post post=new Post();
//		post.setArticleId(rs.getInt("article_id"));
//		post.setBoardId(rs.getInt("board_id"));
//		post.setContext(rs.getString("context"));
//		post.setCreatedDate(rs.getTimestamp("created_date"));
//		post.setEmail(rs.getString("email"));
//		post.setImg(rs.getString("img"));
//		post.setShield(rs.getBoolean("shield"));
//		post.setTitle(rs.getString("title"));
		return likeRequest;
	}
	
}
