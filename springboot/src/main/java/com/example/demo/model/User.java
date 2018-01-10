package com.example.demo.model;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class User {
	
	private String userId;
	
	private String userName;
	
	private Date createTime;
	
	private Integer age;
	
	public User(String userId, String userName, Date createTime) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.createTime = createTime;
	}

	public User() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", createTime=" + createTime + ", age=" + age
				+ "]";
	}


}
