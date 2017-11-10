package com.edu118.bookstore.user.domain;

public class User {
	/*
	 *  uid CHAR(32) PRIMARY KEY,
  		username VARCHAR(50) NOT NULL,
  		`password` VARCHAR(50) NOT NULL,
  		email VARCHAR(50) NOT NULL,
  		`code` CHAR(64) NOT NULL,/
  		state BOOLEAN
	 */
	private String uid;
	private String username;
	private String password;
	private String email;
	private String code;
	private Integer state;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", email=" + email + ", code="
				+ code + ", state=" + state + "]";
	}
	
}
