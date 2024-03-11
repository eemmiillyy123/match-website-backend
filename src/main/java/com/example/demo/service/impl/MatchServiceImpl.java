package com.example.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.MatchDao;
import com.example.demo.dao.PostDao;
import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.MatchResult;
import com.example.demo.model.User;
import com.example.demo.service.MatchService;
import com.example.demo.service.PostService;

@Service
@PropertySource("classpath:path.properties")
public class MatchServiceImpl implements MatchService{
	@Autowired
	private MatchDao matchDao;
	@Autowired
	private PostDao postDao;
	@Autowired 
	private  PostService postService;
	@Value("${upload.folder.path}")
    private String uploadFolderPath;

    @Value("${upload.file.path}")
    private String uploadFilePath;
	
	@Override
	@Transactional
	public Integer saveMatchIntroduce(MatchIntroduceRequest matchIntroduceRequest) {
		return matchDao.saveMatchIntroduce(matchIntroduceRequest);
	}

	@Override
	public MatchIntroduce getMatchIntroduce(Integer userId) {
		MatchIntroduce matchIntroduce = matchDao.getMatchIntroduce(userId);
		 if (matchIntroduce != null && matchIntroduce.getImg() != null) {
		        try {
		        	 File imageFile = ResourceUtils.getFile(uploadFolderPath + matchIntroduce.getImg());
		             // 讀取檔案的 MIME 類型
		             String mimeType = Files.probeContentType(imageFile.toPath());
		             byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
		             String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		             // 根據 MIME 類型設定 base64 圖片資料的前綴
		             String base64ImageWithPrefix = "data:" + mimeType + ";base64," + base64Image;
		            matchIntroduce.setImg(base64ImageWithPrefix);
		            return matchIntroduce;
		        } catch (IOException e) {
		            e.printStackTrace();
		            return null;
		        }
		    } else {
		        return null;
		    }
	}
	
	
	@Override
	@Transactional
	public MatchIntroduce match(MatchIntroduce matchIntroduce) {
		List <Integer> list=getAllUserId(matchIntroduce.getUserId());
		MatchIntroduce anotherPeople = new MatchIntroduce();
		double score;
		double getMaxScore=0;
		Integer matchUserId=0;
		
		MatchIntroduce matchPeople=getMatchIntroduce(matchUserId);
		MatchResult a=matchDao.confirmUserIsMating(matchIntroduce.getUserId());
		if(a!=null&&!a.isResult()) {	
			return getMatchIntroduce(a.getUserBId());
			 
		}
		MatchResult b=matchDao.isMating(matchIntroduce.getUserId());
		if(b!=null&&!b.isResult()) {
			matchDao.saveMatch(matchIntroduce.getUserId(),getMatchIntroduce(b.getUserAId()));
			return getMatchIntroduce(b.getUserBId());
			 
		}
		for (Integer userId : list) {
			if(matchDao.confirmUserIsMating(userId)!=null || matchDao.isMating(userId)!=null) {
				continue;
			}
			else {
				// 計算匹配度
				anotherPeople=matchDao.getMatchIntroduce(userId);
				score = calculateInterestScore(matchIntroduce.getHabit(), anotherPeople.getHabit());	
				if(score>getMaxScore){
					getMaxScore=score;
					matchUserId=userId;
				}
				System.out.println("userId:"+userId+" interest matching score: " + score);
			}
			
		}
			System.out.println("matchUserId:"+matchUserId);
			System.out.println("matchIntroduceId:"+matchIntroduce.getUserId());
			if(matchUserId==0) {
				return null;
			}
			Integer count= matchDao.saveMatch(matchIntroduce.getUserId(),getMatchIntroduce(matchUserId));	
			return matchPeople;
	}

	private List <Integer> getAllUserId(Integer id) {
	return matchDao.getAllUserId(id);
}

	// 計算興趣的匹配度
    private static double calculateInterestScore(String interest1, String interest2) {
        String[] interests1 = interest1.split(",");
        String[] interests2 = interest2.split(",");
        // 計算重複的興趣數量
        int commonInterests = countCommonInterests(interests1, interests2);
        // 計算總共的興趣數量
        int totalInterests = interests1.length + interests2.length;
        // 計算匹配度
        double score = (double) commonInterests / totalInterests;

        return score;
    }

    private static int countCommonInterests(String[] interests1, String[] interests2) {
        Set<String> interestSet = new HashSet<>();
        for (String interest : interests1) {
            interestSet.add(interest.trim());
        }
        // 計算重複的興趣數量
        int count = 0;
        for (String interest : interests2) {
            if (interestSet.contains(interest.trim())) {
                count++;
            }
        }
        return count;
    }

	@Override
	@Transactional
	public MatchResult willingToMatch(MatchResult result) {
		return matchDao.willingToMatch(result);
	}

	@Override
	@Transactional
	public Integer updataMatchIntroduce(MultipartFile file, Integer id, String name, String companyName,
			String department, String tall, String habit, boolean matchState) throws IOException {
		String img="";
		try {
			if(file==null) {
				img="";
				
			}else {
				String[] fileType = file.getOriginalFilename().split("\\.");
		    	String fileName = "userId" + id + "IntroduceImage." + fileType[1];
		    	String path = uploadFolderPath +"/"+ fileName;
		    	String frontPath =  fileName;
		         Path folderPath = Paths.get(uploadFolderPath);
		         if (!Files.exists(folderPath)) {
		             Files.createDirectories(folderPath); // 如果目錄不存在，則創建它
		         }
		         
		         Path filePath = Paths.get(path);
		         file.transferTo(filePath);

		        img=frontPath;	
			}
			return matchDao.updataMatchIntroduce(img,id, name,companyName,department,tall, habit, matchState);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public MatchIntroduce hasNewMatches(MatchIntroduce matchIntroduce) {
		if(matchDao.hasNewMatches()) {
			return match(matchIntroduce);
		}
		return null;
	}

	@Override
	@Transactional
	public boolean confirmMatchResult(Integer userAId, Integer userBId) {
		if(matchDao.confirmMatchResult(userAId,userBId)) {
			if(matchDao.confirmMatchResult(userBId,userAId)) {
				matchDao.hasResult(userAId,userBId);
				matchDao.hasResult(userBId,userAId);
				return true;
			}
			else {
				
			}
		}
		return false;
	}

	@Override
	public MatchResult notwillingToMatch(MatchResult result) {
		return matchDao.notwillingToMatch(result);
	}

}
