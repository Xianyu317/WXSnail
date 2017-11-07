package com.snail.model;

public class AppUser {

	private String openId;
	private String sessionKey;
	private String unionId;
	
	
	public AppUser() {
		super();
	}


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public String getSessionKey() {
		return sessionKey;
	}


	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}


	public String getUnionId() {
		return unionId;
	}


	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	

}
