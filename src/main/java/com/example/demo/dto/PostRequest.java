package com.example.demo.dto;

import java.util.Date;

public class PostRequest {
	private String email;
	private String board;
	private String title;
	private String context;
	private String img;
	private Date createdDate;
	private Integer articleId;
	private Boolean shield;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
	    this.articleId = articleId;
	}

	public Boolean getShield() {
		return shield;
	}
	public void setShield(Boolean shield) {
		this.shield = shield;
	}
	
}
