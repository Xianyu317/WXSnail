package com.snail.model;

import java.util.Date;

public class FeedBack {

	private int feedId;
	private int userId;
	private String feedContent;
	private String state;
	private Date createTime;
	private Date updateTime;
	
	
	
	public FeedBack() {
		super();
	}
	public FeedBack(int feedId, int userId, String feedContent, String state, Date createTime, Date updateTime) {
		super();
		this.feedId = feedId;
		this.userId = userId;
		this.feedContent = feedContent;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "FeedBack [feedId=" + feedId + ", userId=" + userId + ", feedContent=" + feedContent + ", state=" + state
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFeedContent() {
		return feedContent;
	}
	public void setFeedContent(String feedContent) {
		this.feedContent = feedContent;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
