package com.example.demo.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.PostDao;
import com.example.demo.dto.LikeRequest;
import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.dto.PostRequest;
import com.example.demo.model.Like;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.PostService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@PropertySource("classpath:path.properties")
public class PostServiceImpl implements  PostService{
	
	@Autowired
	private PostDao postDao;
	
	@Value("${upload.folder.path}")
    private String uploadFolderPath;

    @Value("${upload.file.path}")
    private String uploadFilePath;

	@Override
	public Integer addPost(String email, String title, String context, String board, MultipartFile file) throws IOException {
		PostRequest postRequest = new PostRequest();
		postRequest.setEmail(email);
		postRequest.setTitle(title);
		postRequest.setContext(context);
		postRequest.setBoard(board);
		try {
			if(file==null) {
				postRequest.setImg("");	
			}else {
				// Save the file to the server
				// String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				// String uploadFolderPath = "images";
		   	    Integer  id=getUserIdByEmail(email).getUserId();
		   	    String[] fileType = file.getOriginalFilename().split("\\.");
				String path = uploadFolderPath  + "/userId"+id+"image." + fileType[1];
				String frontPath = uploadFilePath +"/userId"+id+"image."+ fileType[1];
				//String path = uploadFolderPath + "/" + fileName;
		        Path folderPath = Paths.get(uploadFolderPath);
		        Path filePath = Paths.get(path);
		        Files.createDirectories(folderPath);
		        file.transferTo(filePath);
	            // Generate the image URL
		        //String imageUrl = "/getImage/app/path/" + fileName; // Adjust as per your URL structure
	            // Save the post with the image URL
				postRequest.setImg(frontPath);		
			}
			return postDao.addPost(postRequest);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
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

	@Override
	public List <Post> getMyPostByEmail(String email) {
		return postDao.getMyPostByEmail(email);
	}

	

	
//	@Override
//	public Post getBoardIdByBoard(PostRequest postRequest) {
//		return postDao.getBoardIdByBoard(postRequest);
//	}

}
