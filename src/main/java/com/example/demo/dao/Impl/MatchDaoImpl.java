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
import com.example.demo.model.User;
import com.example.demo.rowMapper.MatchIntroduceRowMapper;
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
		String sql="select name,img,company_name,department,habit,tall,match_state from match_introduce where user_id=:userId";
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId );
		try {
			return namedParameterJdbcTemplate.queryForObject(sql, map, new MatchIntroduceRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
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
