package com.example.demo.service;

import com.example.demo.dto.PostRequest;
import com.example.demo.model.Post;

public interface PostService {

	 Integer addPost(PostRequest postRequest);
//	 Post getBoardIdByBoard(PostRequest postRequest);
	Post getPostByArticleId(Integer articleId);
}
