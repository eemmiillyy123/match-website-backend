package com.example.demo.dao.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.MatchDao;
import com.example.demo.dao.PostDao;
import com.example.demo.dto.MatchIntroduceRequest;
import com.example.demo.model.MatchIntroduce;
import com.example.demo.model.MatchResult;
import com.example.demo.model.User;
import com.example.demo.rowMapper.MatchIntroduceRowMapper;
import com.example.demo.rowMapper.MatchResultRowMapper;
import com.example.demo.rowMapper.UserRowMapper;
@Component
public class MatchDaoImpl implements MatchDao{
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private PostDao postDao;
	
	@Override
	@Transactional
	public Integer saveMatchIntroduce(MatchIntroduceRequest matchIntroduceRequest) {
		User user=postDao.getUserIdByEmail(matchIntroduceRequest.getEmail());
		String sql="insert into match_introduce(name,company_name,department,img,habit,tall,user_id,match_state) values(:name,:companyName,:department,:img,:habit,:tall,:userId,:matchState)";
		Map<String,Object> map=new HashMap<>();
		map.put("name", matchIntroduceRequest.getName());
		map.put("companyName", matchIntroduceRequest.getCompanyName());
		map.put("department", matchIntroduceRequest.getDepartment());
		map.put("img", matchIntroduceRequest.getImg());
		map.put("habit", matchIntroduceRequest.getHabit());
		map.put("tall", matchIntroduceRequest.getTall());
		map.put("userId", user.getUserId());
		map.put("matchState", matchIntroduceRequest.isMatchState());
//		// 手动指定 habit 参数为 VARCHAR 类型
//		SqlParameterSource sqlParameterSource = new MapSqlParameterSource(map) {
//		    {
//		        // 将 habit 参数指定为 VARCHAR 类型
//		        registerSqlType("habit", Types.VARCHAR);
//		    }
//		};
//
//		Integer count = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
		Integer count=namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
		return count;
	}

	@Override
	public MatchIntroduce getMatchIntroduce(Integer userId) {
		String sql="select name,img,company_name,department,habit,tall,user_id,match_state from match_introduce where user_id=:userId";
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId );
		try {
			return namedParameterJdbcTemplate.queryForObject(sql, map, new MatchIntroduceRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Integer updataMatchIntroduce(String img, Integer id, String name, String companyName, String department,
			String tall, String habit, boolean matchState) {
		String sql = "UPDATE match_introduce SET user_id = :id,name=:name, company_name = :companyName, department = :department, tall = :tall, habit = :habit, match_State = :matchState ";

	    Map<String,Object> map = new HashMap<>();
	    map.put("id", id);
	    map.put("companyName", companyName);
	    map.put("department", department);
	    map.put("tall", tall);
	    map.put("habit", habit);
	    map.put("matchState", matchState);
	    map.put("name", name); 
	    
	    if (!img.isEmpty()) {
	        map.put("img", img);
	        sql += ", img = :img "; 
	    }
	    
	    sql += "WHERE user_id = :id ";
		Integer n= namedParameterJdbcTemplate.update(sql, map);
		return n;

	}

	
	@Override
	@Transactional
	public Integer saveMatch(Integer userAId, MatchIntroduce userB) {
		Integer userBId=userB.getUserId();
		String sql="insert into match_result (user_id_of_a,user_id_of_b) values(:userAId,:userBId)";
		Map<String,Object> map=new HashMap<>();
		map.put("userAId", userAId);
		map.put("userBId", userBId);
		Integer count=namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
		return count;
	}
	
	@Override
	public MatchResult getMatchByUser(Integer userBId) {
		String sql="select choose,result,user_id_of_a,user_id_of_b from match_result where user_id_of_a=:userAId && user_id_of_b=:userBId";
		Map<String,Object> map=new HashMap<>();
//		map.put("userAId", userAId );
		map.put("userBId", userBId );
		try {
			MatchResult re= namedParameterJdbcTemplate.queryForObject(sql, map, new MatchResultRowMapper());
			return re;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public MatchResult getMatchByTwo(Integer userAId,Integer userBId) {
		String sql="select choose,result,user_id_of_a,user_id_of_b from match_result where user_id_of_a=:userAId && user_id_of_b=:userBId";
		Map<String,Object> map=new HashMap<>();
		map.put("userAId", userAId );
		map.put("userBId", userBId );
		try {
			MatchResult re= namedParameterJdbcTemplate.queryForObject(sql, map, new MatchResultRowMapper());
			return re;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public MatchResult confirmUserIsMating(Integer userBId) {
		String sql="select choose,result,user_id_of_a,user_id_of_b from match_result where user_id_of_a=:userBId";
		Map<String,Object> map=new HashMap<>();
		map.put("userBId", userBId );
		try {
			MatchResult re= namedParameterJdbcTemplate.queryForObject(sql, map, new MatchResultRowMapper());
			return re;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public MatchResult isMating(Integer userAId) {
		String sql="select choose,result,user_id_of_a,user_id_of_b from match_result where user_id_of_b=:userAId";
		Map<String,Object> map=new HashMap<>();
		map.put("userAId", userAId );
//		map.put("userAId", userAId );
		try {
			MatchResult re= namedParameterJdbcTemplate.queryForObject(sql, map, new MatchResultRowMapper());
			return re;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public MatchResult willingToMatch(MatchResult result) {
		Integer userAId=result.getUserAId();
		Integer userBId=result.getUserBId();
		String sql="update match_result set choose=:choose where user_id_of_a=:userAId and user_id_of_b=:userBId";
		Map<String,Object> map=new HashMap<>();
		map.put("userAId", userAId);
		map.put("userBId", userBId);
		map.put("choose", true);
		map.put("result", false);
		namedParameterJdbcTemplate.update(sql, map);
		return null;
	}

	@Override
	public List <Integer> getAllUserId(Integer id) {
		String sql="select user_id from match_introduce where user_id!=:userId";
		Map<String,Object> map=new HashMap<>();
		map.put("userId", id );
		List <Integer> list=namedParameterJdbcTemplate.queryForList(sql, map, Integer.class);
		if(list.isEmpty()) {
			return null;
		}
		else {
			return list;
		}
	}

	@Override
	public boolean hasNewMatches() {
		 // 编写查询逻辑以检查是否有新配对数据
        String sql = "SELECT COUNT(*) FROM matches WHERE user_id = true";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count > 0;
	}






//	@Override
//	public List<Map<String, Object>> getAllIntroduce(Integer userId) {
//		String sql="select name,img,company_name,department,habit,tall,user_id,match_state from match_introduce";
////		
//		try {
////			return jdbcTemplate.queryForList(sql);
//////			return namedParameterJdbcTemplate.queryForList(sql,map);
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
//	}

}
