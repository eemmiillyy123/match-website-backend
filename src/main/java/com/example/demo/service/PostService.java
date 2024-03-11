package com.example.demo.service;


import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LikeRequest;
import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.dto.PostRequest;
import com.example.demo.model.Article;
import com.example.demo.model.Like;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.Post;
import com.example.demo.model.User;

public interface PostService {

//	 Integer addPost(PostRequest postRequest);
//	 Post getBoardIdByBoard(PostRequest postRequest);
	Post getPostByArticleId(Integer articleId);
	List<PostRequest> getPosts();
	List<PostRequest> getPostByBoard(String board, String search);
	Integer clickLike(LikeRequest likeRequest);
//	Integer addPost(String email, String title, String context, String board, String imageUrl);
	User getUserIdByEmail(String email) ;
	Integer likeQuantity(Integer articleId);
	Integer searchByUserIdArticleId(Integer articleId,String email);
	Integer addPost(String email, String title, String context, String board, MultipartFile file) throws IOException;
	List<PostRequest> getMyPostByEmail(String email);
}
