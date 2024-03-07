package com.example.demo.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.Article;
import com.example.demo.model.MatchResult;

public class MatchResultRowMapper  implements RowMapper<MatchResult>{
	@Override
	public MatchResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		MatchResult matchResult=new MatchResult();
//		article.setBoard(rs.getString("board"));
		matchResult.setChoose(rs.getBoolean("choose"));
		matchResult.setResult(rs.getBoolean("result"));
		matchResult.setUserBId(rs.getInt("user_id_of_b"));
		matchResult.setUserAId(rs.getInt("user_id_of_a"));
		return matchResult;
	}
	
}
