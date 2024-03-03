package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

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

import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.MatchIntroduce;
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
	public ResponseEntity<String> saveMatchIntroduce(@RequestParam("file") MultipartFile file, @RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("companyName") String companyName, @RequestParam("department") String department,
			@RequestParam("tall") String tall,@RequestParam("habit") String habit,@RequestParam("matchState") boolean matchState){
		
		 try {
		    	Integer  id=postService.getUserIdByEmail(email).getUserId();
		    	String[] fileType = file.getOriginalFilename().split("\\.");
				String path = uploadFolderPath  + "/userId"+id+"IntroduceImage." + fileType[1];
				String frontPath = uploadFilePath +"/userId"+id+"IntroduceImage."+ fileType[1];
//		         String path = uploadFolderPath + "/" + fileName;
		         Path folderPath = Paths.get(uploadFolderPath);
		         Path filePath = Paths.get(path);
		         Files.createDirectories(folderPath);
		         file.transferTo(filePath);
		         
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
		         return ResponseEntity.ok("Post added successfully!");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add post!");
		    }
	}
	
	@GetMapping("/getMatchIntroduce")
	public ResponseEntity<MatchIntroduce> getMatchIntroduce(@RequestParam  Integer userId){	
		MatchIntroduce list=matchService.getMatchIntroduce(userId);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	@PostMapping("/match")
	public ResponseEntity<MatchIntroduce> match(@RequestBody  MatchIntroduceRequest matchIntroduceRequest){
		MatchIntroduce match=matchService.match(matchIntroduceRequest);
		return ResponseEntity.status(HttpStatus.OK).body(match);
	}
}
