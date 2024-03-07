package com.example.demo.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.Article;
import com.example.demo.model.MatchIntroduce;

public class MatchIntroduceRowMapper  implements RowMapper<MatchIntroduce>{
	@Override
	public MatchIntroduce mapRow(ResultSet rs, int rowNum) throws SQLException {
		MatchIntroduce matchIntroduce=new MatchIntroduce();
		matchIntroduce.setName(rs.getString("name"));
		matchIntroduce.setCompanyName(rs.getString("company_name"));
		matchIntroduce.setDepartment(rs.getString("department"));
		matchIntroduce.setHabit(rs.getString("habit"));
		matchIntroduce.setImg(rs.getString("img"));
		matchIntroduce.setMatchState(rs.getBoolean("match_state"));
		matchIntroduce.setTall(rs.getString("tall"));
		matchIntroduce.setUserId(rs.getInt("user_id"));
		return matchIntroduce;
	}
	
}