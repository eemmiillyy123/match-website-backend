package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.MatchResult;

public interface MatchService {

	Integer saveMatchIntroduce(MatchIntroduceRequest matchIntroduceRequest);
	MatchIntroduce getMatchIntroduce(Integer userId);
//	List<Map<String, Object>> getAllIntroduce();
//	List<MatchIntroduceRequest> getAllIntroduce(Integer userId);
	MatchIntroduce match(MatchIntroduce matchIntroduce);
	MatchResult willingToMatch(MatchResult result);
	Integer updataMatchIntroduce(MultipartFile file, Integer id, String name, String companyName, String department,
			String tall, String habit, boolean matchState) throws IOException;

}
