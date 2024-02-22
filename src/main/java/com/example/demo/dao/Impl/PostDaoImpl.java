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

import com.example.demo.dao.PostDao;
import com.example.demo.dto.PostRequest;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.rowMapper.PostRowMapper;
@Component
public class PostDaoImpl implements PostDao{
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Override
	public Integer addPost(PostRequest postRequest) {

		String sql="insert into article(article_id,email,title,context,board_id,created_date,shield,img) values(:articleId,:email,:title,:context,:boardId,:createdDate,:shield,:img)";
		Map<String,Object> map=new HashMap<>();
		map.put("email", postRequest.getEmail() );
		map.put("title", postRequest.getTitle());
		map.put("context", postRequest.getContext() );
		map.put("img", postRequest.getImg() );
		Date date=new Date();
		map.put("createdDate", date );
		map.put("boardId",getBoardIdByBoard(postRequest).getBoardId() );
		map.put("shield", "0" );
		KeyHolder keyHolder=new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		int articleId=keyHolder.getKey().intValue();
		return articleId;
	}

	@Override
	public Post getBoardIdByBoard(PostRequest postRequest) {
		String sql="select board_id from article_board where board=:board";
		Map<String,Object> map=new HashMap<>();
		map.put("board", postRequest.getBoard() );
		List <Post> list=namedParameterJdbcTemplate.query(sql, map, new PostRowMapper());
		if(list.isEmpty()) {
			return null;
		}
		else {
			return list.get(0);
		}
	}

	@Override
	public Post getPostByArticleId(Integer articleId) {
		String sql="select article_id,email,title,context,board_id,created_date,shield,img from article where article_id=:articleId";
		Map<String,Object> map=new HashMap<>();
		map.put("articleId", articleId );
		List <Post> list=namedParameterJdbcTemplate.query(sql, map, new PostRowMapper());
		if(list.isEmpty()) {
			return null;
		}
		else {
			return list.get(0);
		}
	}

}
