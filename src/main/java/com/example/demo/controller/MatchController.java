package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.MatchResult;
import com.example.demo.model.User;
import com.example.demo.service.MatchService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;

@RestController
@PropertySource("classpath:path.properties")
public class MatchController {
	@Autowired 
	private MatchService matchService;
	@Autowired 
	private  PostService postService;
	
	@Value("${upload.folder.path}")
    private String uploadFolderPath;

    @Value("${upload.file.path}")
    private String uploadFilePath;
    
	@PostMapping("/saveMatchIntroduce")
	public ResponseEntity<MatchIntroduce> saveMatchIntroduce(@RequestParam("file") MultipartFile file, @RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("companyName") String companyName, @RequestParam("department") String department,
			@RequestParam("tall") String tall,@RequestParam("habit") String habit,@RequestParam("matchState") boolean matchState){
		
		 try {
		    	Integer  id=postService.getUserIdByEmail(email).getUserId();
		    	String[] fileType = file.getOriginalFilename().split("\\.");
		    	String fileName = "userId" + id + "IntroduceImage." + fileType[1];
		    	String path = uploadFolderPath +"/"+ fileName;
		    	String frontPath =  fileName;
//				String path = uploadFolderPath  + "/userId"+id+"IntroduceImage." + fileType[1];
//				String frontPath = uploadFilePath +"/userId"+id+"IntroduceImage."+ fileType[1];
//		         String path = uploadFolderPath + "/" + fileName;
		         Path folderPath = Paths.get(uploadFolderPath);
		         if (!Files.exists(folderPath)) {
		             Files.createDirectories(folderPath); // 如果目錄不存在，則創建它
		         }
		         
		         Path filePath = Paths.get(path);
		         file.transferTo(filePath);
//		         Path filePath = Paths.get(path);
//		         Files.createDirectories(folderPath);
//		         file.transferTo(filePath);
		         
		         MatchIntroduceRequest matchIntroduceRequest=new MatchIntroduceRequest();
		         matchIntroduceRequest.setImg(frontPath);
		         matchIntroduceRequest.setName(name);
		         matchIntroduceRequest.setEmail(email);
		         matchIntroduceRequest.setCompanyName(companyName);
		         matchIntroduceRequest.setDepartment(department);
		         matchIntroduceRequest.setHabit(habit);
		         matchIntroduceRequest.setMatchState(matchState);
		         matchIntroduceRequest.setTall(tall);
		         Integer count=matchService.saveMatchIntroduce(matchIntroduceRequest);
		         MatchIntroduce list=matchService.getMatchIntroduce(id);
		         return  ResponseEntity.status(HttpStatus.OK).body(list);
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.OK).body(null);
		    }
	}
	
	@GetMapping("/getMatchIntroduce")
	public ResponseEntity<MatchIntroduce> getMatchIntroduce(@RequestParam  Integer userId){	
//		MatchIntroduce list=matchService.getMatchIntroduce(userId);
		 MatchIntroduce matchIntroduce = matchService.getMatchIntroduce(userId);
//		    if (matchIntroduce != null && matchIntroduce.getImg() != null) {
//		        try {
//		            File imageFile = ResourceUtils.getFile(uploadFolderPath + File.separator  + matchIntroduce.getImg());
//		            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
//		            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//		            matchIntroduce.setImg(base64Image);
//		            return ResponseEntity.ok(matchIntroduce);
//		        } catch (IOException e) {
//		            e.printStackTrace();
//		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//		        }
//		    } else {
//		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//		    }
		return ResponseEntity.status(HttpStatus.OK).body(matchIntroduce);
	}
	
	@PostMapping("/updataMatchIntroduce")
	public ResponseEntity<MatchIntroduce> updataMatchIntroduce(@RequestParam(required = false) MultipartFile file, @RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("companyName") String companyName, @RequestParam("department") String department,
			@RequestParam("tall") String tall,@RequestParam("habit") String habit,@RequestParam("matchState") boolean matchState){
		
		 try {
		    	Integer  id=postService.getUserIdByEmail(email).getUserId();
		         Integer count=matchService.updataMatchIntroduce(file,id, name,companyName,department,tall, habit, matchState);
		         MatchIntroduce list=matchService.getMatchIntroduce(id);
		         return  ResponseEntity.status(HttpStatus.OK).body(list);
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.OK).body(null);
		    }
	}
	
	@PostMapping("/match")
	public ResponseEntity<MatchIntroduce> match(@RequestBody  MatchIntroduce matchIntroduce){
		MatchIntroduce match=matchService.match(matchIntroduce);
		return ResponseEntity.status(HttpStatus.OK).body(match);
	}
	
	@PostMapping("/willingToMatch")
	public ResponseEntity<MatchResult> willingToMatch(@RequestBody MatchResult result){
		MatchResult match=matchService.willingToMatch(result);
		return ResponseEntity.status(HttpStatus.OK).body(match);
	}
}
