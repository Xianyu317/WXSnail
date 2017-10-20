package com.snail.model;

import java.util.Date;

public class UserPoint {
	
	private int pointId; 
	private int userId;
	private int pointValue;
	private int state;
	private Date createTime;
	private Date updateTime;
	
	public UserPoint() {
		super();
	}

	public UserPoint(int pointId, int userId, int pointValue, int state, Date createTime, Date updateTime) {
		super();
		this.pointId = pointId;
		this.userId = userId;
		this.pointValue = pointValue;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "UserPoint [pointId=" + pointId + ", userId=" + userId + ", pointValue=" + pointValue + ", state="
				+ state + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPointValue() {
		return pointValue;
	}
	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
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
