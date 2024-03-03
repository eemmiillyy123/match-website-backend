package com.example.demo.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MatchDao;
import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.service.MatchService;

@Service
public class MatchServiceImpl implements MatchService{
	@Autowired
	private MatchDao matchDao;
	
	@Override
	public Integer saveMatchIntroduce(MatchIntroduceRequest matchIntroduceRequest) {
		return matchDao.saveMatchIntroduce(matchIntroduceRequest);
	}

	@Override
	public MatchIntroduce getMatchIntroduce(Integer userId) {
		
		return matchDao.getMatchIntroduce(userId);
	}
	
//	@Override
//	public List<MatchIntroduceRequest>  getAllIntroduce(Integer userId) {
//		List<MatchIntroduceRequest> list= matchDao.getAllIntroduce(userId);
//		return list;
//	}
	
	@Override
	public MatchIntroduce match(MatchIntroduceRequest matchIntroduceRequest) {
//		matchDao.getAllIntroduce(30);
		MatchIntroduce anotherPeople=matchDao.getMatchIntroduce(30);
		// 计算兴趣匹配度
        double score = calculateInterestScore(matchIntroduceRequest.getHabit(), anotherPeople.getHabit());

        // 打印匹配度
        System.out.println("Interest matching score: " + score);
		return anotherPeople;
	}

	// 计算兴趣的匹配度的方法
    private static double calculateInterestScore(String interest1, String interest2) {
        // 将兴趣字符串分割为单词
        String[] interests1 = interest1.split(",");
        String[] interests2 = interest2.split(",");

        // 计算重复的兴趣数量
        int commonInterests = countCommonInterests(interests1, interests2);

        // 计算总共的兴趣数量
        int totalInterests = interests1.length + interests2.length;

        // 计算匹配度
        double score = (double) commonInterests / totalInterests;

        return score;
    }

    // 辅助方法：计算重复的兴趣数量
    private static int countCommonInterests(String[] interests1, String[] interests2) {
        // 将兴趣存储到集合中以便检查重复
        Set<String> interestSet = new HashSet<>();
        for (String interest : interests1) {
            interestSet.add(interest.trim());
        }

        // 计算重复的兴趣数量
        int count = 0;
        for (String interest : interests2) {
            if (interestSet.contains(interest.trim())) {
                count++;
            }
        }

        return count;
    }

}
