package com.example.demo.dao;

import java.util.List;

import com.example.demo.dto.PostRequest;
import com.example.demo.model.Article;
import com.example.demo.model.Post;

public interface PostDao {

	Integer addPost(PostRequest postRequest);

	Article getBoardIdByBoard(PostRequest postRequest);

	Post getPostByArticleId(Integer articleId);

	List<PostRequest> getProducts();

	List<PostRequest> getPostByBoard(String board, String search);


}
