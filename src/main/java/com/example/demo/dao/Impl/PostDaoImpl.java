package com.example.demo.dao.Impl;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.PostDao;
import com.example.demo.dto.LikeRequest;
import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.dto.PostRequest;
import com.example.demo.model.Article;
import com.example.demo.model.Like;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.rowMapper.ArticleRowMapper;
import com.example.demo.rowMapper.LikeRowMapper;
import com.example.demo.rowMapper.MatchIntroduceRowMapper;
import com.example.demo.rowMapper.PostRequestRowMapper;
import com.example.demo.rowMapper.PostRowMapper;
import com.example.demo.rowMapper.UserRowMapper;
import com.example.demo.service.impl.PostServiceImpl;
@Component
public class PostDaoImpl implements PostDao{
	private final static Logger log=LoggerFactory.getLogger(PostDaoImpl.class);
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	//新增貼文
	@Transactional
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
		try {
			KeyHolder keyHolder=new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
			int articleId=keyHolder.getKey().intValue();
			return articleId;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
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

	//取得所有貼文
	@Override
	public List<PostRequest> getPosts() {
//		String sql="select email,title,context,board_id,img from article";
		String sql = "SELECT a.article_id, a.email, a.title, a.context, a.board_id, a.img, b.board,a.created_date ,a.shield " +
                "FROM article a " +
                "JOIN article_board b ON a.board_id = b.board_id "+
                "ORDER BY a.created_date DESC";
		Map<String,Object> map=new HashMap<>();
		List <PostRequest> list=namedParameterJdbcTemplate.query(sql, map, new PostRequestRowMapper());
		if(list.isEmpty()) {
			return null;
		}
		else {
			return list;
		}
	}

	//點擊看板會顯示的貼文
	@Override
	public List<PostRequest> getPostByBoard(String board, String search) {
		String sql = "SELECT a.article_id, a.email, a.title, a.context, a.board_id, a.img,a.created_date, b.board ,a.shield " +
                "FROM article a " +
                "JOIN article_board b ON a.board_id = b.board_id where 1=1 ";
		//1=1不會影響查詢結果 是為了讓查詢條件可以自由的拼接在sql後面
		//是spring jdbctemplate中好用的解法 使用jpa的話不會有這個問題 他會自己處理多個條件的組合問題		
		Map<String,Object> map=new HashMap<>();
		 if (board != null && search != null) {
		        sql += " AND board = :board AND (title LIKE :search OR context LIKE :search) ORDER BY a.created_date DESC";
		        map.put("board", board);
		        map.put("search", "%" + search + "%");
		    } else if (board != null) {
		        sql += " AND board = :board ORDER BY a.created_date DESC";
		        map.put("board", board);
		    } else if (search != null) {
		        sql += " AND (title LIKE :search OR context LIKE :search) ORDER BY a.created_date DESC";
		        map.put("search", "%" + search + "%");
		    }
		List<PostRequest> list=namedParameterJdbcTemplate.query(sql, map, new PostRequestRowMapper());
		return list;
	}

	

	@Override
	public Integer searchByUserIdArticleId(Integer articleId,Integer userId) {
		String sql="select count(*) from clicklike where article_id=:articleId and user_id=:userId ";
		Map<String,Object> map=new HashMap<>();
		map.put("articleId", articleId );
		map.put("userId", userId );
//		// 使用 query 方法执行查询
//	    List<Integer> counts = namedParameterJdbcTemplate.query(sql, map, );
//	    
//	    // 检查结果列表是否为空
//	    if (counts.isEmpty()) {
//	        return 0;
//	    } else {
//	        return counts.get(0);
//	    }
//		return  namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
		try {
			
			Integer count= namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
			return count;
		} catch (EmptyResultDataAccessException e) {
			return -1;
		}
	}
	
	@Override
	public User getUserIdByEmail(String email) {
		String sql="select user_id,email,password,created_date,last_modified_date,username from user where email=:email";
		Map<String,Object> map=new HashMap<>();
		map.put("email", email );
		try {
			return namedParameterJdbcTemplate.queryForObject(sql, map, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}	
	}
	
	//按讚
	@Transactional
	@Override
	public Integer clickLike(Integer articleId, Integer userId) {
		String sql="insert into clicklike (article_id,user_id) values(:articleId,:userId)";
		Map<String,Object> map=new HashMap<>();
		map.put("articleId", articleId);
		map.put("userId", userId);
		Integer like=namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
		return likeQuantity(articleId );
	}
	
	@Override
	public Integer likeQuantity(Integer articleId) {
		String sql="select count(*) from clicklike where article_id=:articleId";
		Map<String,Object> map=new HashMap<>();
		map.put("articleId", articleId );
		Integer total=  namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
		//使用count時可以使用queryForObject將count值轉成integer類型
		return total;
	}

	@Override
	@Transactional
	public Integer  cancleLike(Integer articleId, Integer userId) {
//		String sql="delete from `like` where article_id=:articleId and user_id=:userId ";
//		Map<String,Object> map=new HashMap<>();
//		map.put("userId", userId);
//		namedParameterJdbcTemplate.update(sql, map);
//		return likeQuantity(articleId );
//	}
		// 先检查是否存在符合条件的点赞记录
	    if (searchByUserIdArticleId(articleId, userId) > 0) {
	        String sql = "DELETE FROM clicklike WHERE article_id = :articleId AND user_id = :userId";
	        Map<String, Object> map = new HashMap<>();
	        map.put("articleId", articleId);
	        map.put("userId", userId);
	        namedParameterJdbcTemplate.update(sql, map);
	    }
	    return likeQuantity(articleId);
	}

	@Override
	public List <Post> getMyPostByEmail(String email) {
		String sql="select article_id,email,title,context,board_id,created_date,shield,img from article where email=:email";
		Map<String,Object> map=new HashMap<>();
		map.put("email", email );
		List <Post> list=namedParameterJdbcTemplate.query(sql, map, new PostRowMapper());
		if(list.isEmpty()) {
			return null;
		}
		else {
			return list;
		}
	}

	
}