package com.example.demo.model;

public class MatchResult {
	private Integer userAId;
	private Integer userBId;
	private boolean choose;
	private boolean result;
	public Integer getUserAId() {
		return userAId;
	}
	public void setUserAId(Integer userAId) {
		this.userAId = userAId;
	}
	public Integer getUserBId() {
		return userBId;
	}
	public void setUserBId(Integer userBId) {
		this.userBId = userBId;
	}
	public boolean isChoose() {
		return choose;
	}
	public void setChoose(boolean choose) {
		this.choose = choose;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
}
