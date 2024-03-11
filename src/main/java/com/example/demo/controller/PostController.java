package com.example.demo.controller;

import java.io.IOException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LikeRequest;
import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.dto.PostRequest;
import com.example.demo.model.Article;
import com.example.demo.model.Like;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.PostService;
import com.example.demo.service.impl.PostServiceImpl;

import org.springframework.util.StringUtils;

@RestController

public class PostController {	
	@Autowired 
	private  PostService postService;
	
	
//	@PostMapping("/addpost")
//	public ResponseEntity<Post> addpost(@RequestBody PostRequest postRequest){
//		Integer articleId=postService.addPost(postRequest);
//		//利用文章id把新增的整筆資料查回來
//		Post post=postService.getPostByArticleId(articleId);
//		return ResponseEntity.status(HttpStatus.OK).body(post);
//	}
	@PostMapping("/addpost")
	public ResponseEntity<String> addPost(@RequestParam(required = false) MultipartFile file, @RequestParam("email") String email, @RequestParam("title") String title, @RequestParam("context") String context, @RequestParam("board") String board) {
	    try {
	       
	        postService.addPost(email, title, context, board, file);

	        return ResponseEntity.ok("Post added successfully!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add post!");
	    }
	}
	
	@GetMapping("/getposts")
	public ResponseEntity<List<PostRequest>> getPosts(){
		List<PostRequest> postList=postService.getPosts();
		return ResponseEntity.status(HttpStatus.OK).body(postList);
	}
	
	@GetMapping("/selectPost")
	public ResponseEntity<List<PostRequest>> getPostByBoard(@RequestParam(required = false) String board,@RequestParam(required = false)String search){	
		List<PostRequest> list=postService.getPostByBoard(board,search);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@GetMapping("/getUserIdByEmail")
	public Integer getUserIdByEmail(@RequestParam String email) {
		return postService.getUserIdByEmail(email).getUserId();
	}
	
	@PostMapping("/clickLike")
	public ResponseEntity<Integer> clickLike(@RequestBody LikeRequest likeRequest){
		Integer number= postService.clickLike(likeRequest);
		return ResponseEntity.status(HttpStatus.OK).body(number);
	}
	
	@GetMapping("/likeQuantity")
	public Integer likeQuantity(@RequestParam Integer articleId) {
		return postService.likeQuantity(articleId);
	}
	
	@GetMapping("/searchByUserIdArticleId")
	public Integer searchByUserIdArticleId(@RequestParam Integer articleId,@RequestParam String email) {
		return  postService.searchByUserIdArticleId(articleId,email);
	}
	
	@GetMapping("/getMyPostByEmail")
	public ResponseEntity<List <PostRequest>> getMyPostByEmail(@RequestParam String email) {
		List <PostRequest> mypost= postService.getMyPostByEmail(email);
		return ResponseEntity.status(HttpStatus.OK).body(mypost);
	}
	
}
