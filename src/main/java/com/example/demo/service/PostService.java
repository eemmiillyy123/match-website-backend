package com.example.demo.service;


import java.util.List;

import com.example.demo.dto.PostRequest;
import com.example.demo.model.Post;

public interface PostService {

	 Integer addPost(PostRequest postRequest);
//	 Post getBoardIdByBoard(PostRequest postRequest);
	Post getPostByArticleId(Integer articleId);
	List<PostRequest> getProducts();
	List<PostRequest> getPostByBoard(String board, String search);
}
