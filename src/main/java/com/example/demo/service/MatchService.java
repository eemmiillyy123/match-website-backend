package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.model.MatchIntroduce;

public interface MatchService {

	Integer saveMatchIntroduce(MatchIntroduceRequest matchIntroduceRequest);
	MatchIntroduce getMatchIntroduce(Integer userId);
	MatchIntroduce match(MatchIntroduceRequest matchIntroduceRequest);
//	List<Map<String, Object>> getAllIntroduce();
//	List<MatchIntroduceRequest> getAllIntroduce(Integer userId);

}
