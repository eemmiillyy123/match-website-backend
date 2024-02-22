package com.example.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.PostDao;
import com.example.demo.dto.PostRequest;
import com.example.demo.model.Post;
import com.example.demo.service.PostService;
@Component
public class PostServiceImpl implements  PostService{
	private final static Logger log=LoggerFactory.getLogger(PostServiceImpl.class);
	@Autowired
	private PostDao postDao;
	
	@Override
	public Integer addPost(PostRequest postRequest) {
//		PostpostDao.getBoardIdByBoard(postRequest);
		return postDao.addPost(postRequest);
	}

	@Override
	public Post getPostByArticleId(Integer articleId) {
		return postDao.getPostByArticleId(articleId);
	}
	
	@Override
	public Post getBoardIdByBoard(PostRequest postRequest) {
		return postDao.getBoardIdByBoard(postRequest);
	}

}
