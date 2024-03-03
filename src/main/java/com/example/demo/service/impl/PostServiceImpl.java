package com.example.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.PostDao;
import com.example.demo.dto.LikeRequest;
import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.dto.PostRequest;
import com.example.demo.model.Like;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.PostService;
@Component
public class PostServiceImpl implements  PostService{
	@Autowired
	private PostDao postDao;
	
//@Override
	//	public Integer addPost(PostRequest postRequest) {
////		PostpostDao.getBoardIdByBoard(postRequest);
//		return postDao.addPost(postRequest);
//	}
	@Override
	public Integer addPost(String email, String title, String context, String board, String imageUrl) {
	    // 创建一个帖子对象
		PostRequest postRequest = new PostRequest();
		postRequest.setEmail(email);
		postRequest.setTitle(title);
		postRequest.setContext(context);
		postRequest.setBoard(board);
		postRequest.setImg(imageUrl);
		return postDao.addPost(postRequest);
	    // 将帖子保存到数据库中
//	    postRepository.save(post);
	}

	@Override
	public Post getPostByArticleId(Integer articleId) {
		return postDao.getPostByArticleId(articleId);
	}

	@Override
	public List<PostRequest> getPosts() {
		return postDao.getPosts();
	}

	@Override
	public List<PostRequest> getPostByBoard(String board, String search) {
		return postDao.getPostByBoard(board,search);
	}

	@Override
	public Integer clickLike(LikeRequest likeRequest) {
		User user=postDao.getUserIdByEmail(likeRequest.getEmail());
		Integer count= postDao.searchByUserIdArticleId(likeRequest.getArticleId(),user.getUserId());
		if(count>0) {
			return postDao.cancleLike(likeRequest.getArticleId(),user.getUserId());
		}
		else {
			return postDao.clickLike(likeRequest.getArticleId(),user.getUserId());			
		}
	}

	@Override
	public User getUserIdByEmail(String email) {
		return postDao.getUserIdByEmail(email);
	}

	@Override
	public Integer likeQuantity(Integer articleId) {
		return postDao.likeQuantity(articleId);
	}

	@Override
	public Integer searchByUserIdArticleId(Integer articleId, String email) {
		return postDao.searchByUserIdArticleId(articleId, postDao.getUserIdByEmail(email).getUserId());
	}

	

	
//	@Override
//	public Post getBoardIdByBoard(PostRequest postRequest) {
//		return postDao.getBoardIdByBoard(postRequest);
//	}

}
