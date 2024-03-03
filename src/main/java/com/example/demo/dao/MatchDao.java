package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.model.MatchIntroduce;

@Component
public interface MatchDao {
	Integer saveMatchIntroduce(MatchIntroduceRequest matchIntroduceRequest);

	MatchIntroduce getMatchIntroduce(Integer userId);

//	List<Map<String, Object>> getAllIntroduce(Integer userId);
}
