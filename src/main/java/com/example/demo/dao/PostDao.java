package com.example.demo.dao;

import java.util.List;

import com.example.demo.dto.LikeRequest;
import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.dto.PostRequest;
import com.example.demo.model.Article;
import com.example.demo.model.Like;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.Post;
import com.example.demo.model.User;

public interface PostDao {

	Integer addPost(PostRequest postRequest);

	Article getBoardIdByBoard(PostRequest postRequest);

	Post getPostByArticleId(Integer articleId);

	List<PostRequest> getPosts();

	List<PostRequest> getPostByBoard(String board, String search);


	User getUserIdByEmail(String email);

	Integer likeQuantity(Integer integer);

	Integer searchByUserIdArticleId(Integer articleId, Integer userId);

	Integer clickLike(Integer articleId, Integer userId);

	Integer cancleLike(Integer articleId, Integer userId);

	List <Post> getMyPostByEmail(String email);

}
