package com.example.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
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
	
	@Value("${upload.postImg.path}")
    private String uploadPostImgPath;

    @Value("${upload.postfile.path}")
    private String uploadPostfilePath;
    
//	@Value("${upload.folder.path}")
//    private String uploadFolderPath;
//
//    @Value("${upload.file.path}")
//    private String uploadFilePath;

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
				Integer  id=postDao.getUserIdByEmail(email).getUserId();
		    	String[] fileType = file.getOriginalFilename().split("\\.");
		    	String fileName = "userId" + id + "PostImage." + fileType[1];
		    	String path = uploadPostImgPath +"/"+ fileName;
		    	String frontPath =  fileName;
//				String path = uploadFolderPath  + "/userId"+id+"IntroduceImage." + fileType[1];
//				String frontPath = uploadFilePath +"/userId"+id+"IntroduceImage."+ fileType[1];
//		         String path = uploadFolderPath + "/" + fileName;
		         Path folderPath = Paths.get(uploadPostImgPath);
		         if (!Files.exists(folderPath)) {
		             Files.createDirectories(folderPath); // 如果目錄不存在，則創建它
		         }
		         
		         Path filePath = Paths.get(path);
		         file.transferTo(filePath);
//		   	    Integer  id=getUserIdByEmail(email).getUserId();
//		   	    String[] fileType = file.getOriginalFilename().split("\\.");
//				String path = uploadFolderPath  + "/userId"+id+"image." + fileType[1];
//				String frontPath = uploadFilePath +"/userId"+id+"image."+ fileType[1];
//		        Path folderPath = Paths.get(uploadFolderPath);
//		        Path filePath = Paths.get(path);
//		        Files.createDirectories(folderPath);
//		        file.transferTo(filePath);
	           
				postRequest.setImg(frontPath);		
			}
			return postDao.addPost(postRequest);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Post getPostByArticleId(Integer articleId) {
		Post post=postDao.getPostByArticleId(articleId);
		String image=readFile(post.getImg());
		post.setImg(image);
		return post;
	}

	@Override
	public List<PostRequest> getPosts() {
		List<PostRequest> list=postDao.getPosts();
		for (PostRequest request : list) {
			String image=readFile(request.getImg());
			request.setImg(image);
		}
		
		return list;
	}

	private String readFile(String img) {
		try {
			File imageFile = ResourceUtils.getFile(uploadPostImgPath + img);
	        // 讀取檔案的 MIME 類型
	        String mimeType = Files.probeContentType(imageFile.toPath());
	        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
	        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
	        // 根據 MIME 類型設定 base64 圖片資料的前綴
	        String base64ImageWithPrefix = "data:" + mimeType + ";base64," + base64Image;
			return base64ImageWithPrefix;
		 } catch (IOException e) {
	            // 處理檔案讀取或其他 IO 錯誤
	            e.printStackTrace();
	            return null; // 或者拋出自定義例外
	        }
	}

	@Override
	public List<PostRequest> getPostByBoard(String board, String search) {
		List<PostRequest> list=postDao.getPostByBoard(board,search);
		for (PostRequest request : list) {
			String image=readFile(request.getImg());
			request.setImg(image);
		}
		
		return list;
	}
	
	
	@Override
	public List <PostRequest> getMyPostByEmail(String email) {
		List<PostRequest> list= postDao.getMyPostByEmail(email);
		for (PostRequest request : list) {
			String image=readFile(request.getImg());
			request.setImg(image);
		}
		
		return list;
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



	

	
//	@Override
//	public Post getBoardIdByBoard(PostRequest postRequest) {
//		return postDao.getBoardIdByBoard(postRequest);
//	}

}
