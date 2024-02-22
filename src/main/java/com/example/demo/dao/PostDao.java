package com.example.demo.dao;

import com.example.demo.dto.PostRequest;
import com.example.demo.model.Post;

public interface PostDao {

	Integer addPost(PostRequest postRequest);

	Post getBoardIdByBoard(PostRequest postRequest);

	Post getPostByArticleId(Integer articleId);


}
