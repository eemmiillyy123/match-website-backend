package com.example.demo.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.Article;

public class ArticleRowMapper  implements RowMapper<Article>{
		@Override
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Article article=new Article();
//			article.setBoard(rs.getString("board"));
			article.setBoardId(rs.getInt("board_id"));
//			Post post=new Post();
//			post.setArticleId(rs.getInt("article_id"));
//			post.setBoardId(rs.getInt("board_id"));
//			post.setContext(rs.getString("context"));
//			post.setCreatedDate(rs.getTimestamp("created_date"));
//			post.setEmail(rs.getString("email"));
//			post.setImg(rs.getString("img"));
//			post.setShield(rs.getBoolean("shield"));
//			post.setTitle(rs.getString("title"));
			return article;
		}
		
	}
