package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PostRequest;
import com.example.demo.model.Post;
import com.example.demo.service.PostService;

@RestController
public class PostController {
	
	@Autowired 
	private  PostService postService;
	
	@PostMapping("/addpost")
	public ResponseEntity<Post> addpost(@RequestBody PostRequest postRequest){
		Integer articleId=postService.addPost(postRequest);
		Post post=postService.getPostByArticleId(articleId);
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}
	
	@GetMapping("/getposts")
	public ResponseEntity<List<PostRequest>> getPosts(){
		List<PostRequest> postList=postService.getProducts();
		return ResponseEntity.status(HttpStatus.OK).body(postList);
	}
	
	@GetMapping("/selectPost")
	public ResponseEntity<List<PostRequest>> getPostByBoard(@RequestParam(required = false) String board,@RequestParam(required = false)String search){
		//category不是必填的:使用required=false		
		List<PostRequest> list=postService.getPostByBoard(board,search);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
}
