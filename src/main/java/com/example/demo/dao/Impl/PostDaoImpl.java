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
import com.example.demo.model.Article;
import com.example.demo.model.Post;
import com.example.demo.rowMapper.ArticleRowMapper;
import com.example.demo.rowMapper.PostRequestRowMapper;
import com.example.demo.rowMapper.PostRowMapper;
@Component
public class PostDaoImpl implements PostDao{
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Override
	public Integer addPost(PostRequest postRequest) {

		String sql="insert into article(email,title,context,board_id,created_date,shield,img) values(:email,:title,:context,:boardId,:createdDate,:shield,:img)";
		Map<String,Object> map=new HashMap<>();
		map.put("email", postRequest.getEmail() );
		map.put("title", postRequest.getTitle());
		map.put("context", postRequest.getContext() );
		map.put("img", postRequest.getImg() );
		Date date=new Date();
		map.put("createdDate", date );
		map.put("boardId",getBoardIdByBoard(postRequest).getBoardId());
		map.put("shield",false  );
		KeyHolder keyHolder=new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		int articleId=keyHolder.getKey().intValue();
		return articleId;
	}

	@Override
	public Article getBoardIdByBoard(PostRequest postRequest) {
		String sql="select board_id from article_board where board=:board";
		Map<String,Object> map=new HashMap<>();
		map.put("board", postRequest.getBoard() );
		List <Article> list=namedParameterJdbcTemplate.query(sql, map, new ArticleRowMapper());
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

	@Override
	public List<PostRequest> getProducts() {
//		String sql="select email,title,context,board_id,img from article";
		String sql = "SELECT a.email, a.title, a.context, a.board_id, a.img, b.board " +
                "FROM article a " +
                "JOIN article_board b ON a.board_id = b.board_id";
		Map<String,Object> map=new HashMap<>();
		List <PostRequest> list=namedParameterJdbcTemplate.query(sql, map, new PostRequestRowMapper());
		if(list.isEmpty()) {
			return null;
		}
		else {
			return list;
		}
	}


	@Override
	public List<PostRequest> getPostByBoard(String board, String search) {
		String sql = "SELECT a.email, a.title, a.context, a.board_id, a.img, b.board " +
                "FROM article a " +
                "JOIN article_board b ON a.board_id = b.board_id where 1=1";
		//1=1不會影響查詢結果 是為了讓查詢條件可以自由的拼接在sql後面
		//是spring jdbctemplate中好用的解法 使用jpa的話不會有這個問題 他會自己處理多個條件的組合問題		
		Map<String,Object> map=new HashMap<>();
		if(board!=null) {
			sql=sql+" and board=:board";
			map.put("board", board);
		}
		if(search!=null) {
			sql=sql+" and (title LIKE :search OR context LIKE :search)"; //LIKE常和%一起用
			map.put("search", "%"+search+"%");//表示只要有search字的都算
		}
		List<PostRequest> list=namedParameterJdbcTemplate.query(sql, map, new PostRequestRowMapper());
		return list;
	}
	}