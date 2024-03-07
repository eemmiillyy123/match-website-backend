package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.MatchResult;
import com.example.demo.rowMapper.MatchIntroduceRowMapper;

@Component
public interface MatchDao {
	Integer saveMatchIntroduce(MatchIntroduceRequest matchIntroduceRequest);

	MatchIntroduce getMatchIntroduce(Integer userId);

	Integer saveMatch(Integer integer, MatchIntroduce anotherPeople);

	MatchResult willingToMatch(MatchResult result);


	Integer updataMatchIntroduce(String img, Integer id, String name, String companyName, String department,
			String tall, String habit, boolean matchState);

	List <Integer> getAllUserId(Integer id);

	MatchResult getMatchByUser(Integer userAId, Integer userBId);

	MatchResult confirmUserIsMating(Integer userAId);

//	List<Map<String, Object>> getAllIntroduce(Integer userId);
}
