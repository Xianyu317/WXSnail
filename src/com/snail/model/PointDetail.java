package com.snail.model;

import java.util.Date;

public class PointDetail {

	private int detailId;
	private int pointId;
	private int userId;
	private int orderId;
	private int pointValue;
	private String remark;
	private int state;
	private Date createTime;
	private Date updateTime;
	
	
	public PointDetail() {
		super();
	}
	
	
	
	public PointDetail(int detailId, int pointId, int userId, int orderId,
			int pointValue, String remark, int state, Date createTime,
			Date updateTime) {
		super();
		this.detailId = detailId;
		this.pointId = pointId;
		this.userId = userId;
		this.orderId = orderId;
		this.pointValue = pointValue;
		this.remark = remark;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}



	@Override
	public String toString() {
		return "PointDetail [detailId=" + detailId + ", pointId=" + pointId
				+ ", userId=" + userId + ", orderId=" + orderId
				+ ", pointValue=" + pointValue + ", remark=" + remark
				+ ", state=" + state + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}



	public int getPointId() {
		return pointId;
	}


	public void setPointId(int pointId) {
		this.pointId = pointId;
	}


	public int getDetailId() {
		return detailId;
	}
	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getPointValue() {
		return pointValue;
	}
	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
