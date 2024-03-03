package com.example.demo.dto;

public class MatchIntroduceRequest {
	private String name;
	private String companyName;
	private String department;
	private String img;
	private String  habit;
	private String tall ;
	private String  email;
	private boolean matchState;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getHabit() {
		return habit;
	}
	public void setHabit(String habit) {
		this.habit = habit;
	}
	public String getTall() {
		return tall;
	}
	public void setTall(String tall) {
		this.tall = tall;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isMatchState() {
		return matchState;
	}
	public void setMatchState(boolean matchState) {
		this.matchState = matchState;
	}
	
}
