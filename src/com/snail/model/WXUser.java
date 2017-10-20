package com.snail.model;

import java.util.Date;

public class WXUser {
	private int userId;
	private int openId;
	private String wxnickName;
	private String realName;
	private String wxAddress;
	private String phoneNum;
	private int loginCount;
	private String remark;
	private String state;
	private Date createTime;
	private Date updateTime;
	
	
	
	public WXUser() {
		super();
	}

	public WXUser(int userId, int openId, String wxnickName, String realName, String wxAddress, String phoneNum,
			int loginCount, String remark, String state, Date createTime, Date updateTime) {
		super();
		this.userId = userId;
		this.openId = openId;
		this.wxnickName = wxnickName;
		this.realName = realName;
		this.wxAddress = wxAddress;
		this.phoneNum = phoneNum;
		this.loginCount = loginCount;
		this.remark = remark;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}


	@Override
	public String toString() {
		return "WXUser [userId=" + userId + ", openId=" + openId + ", wxnickName=" + wxnickName + ", realName="
				+ realName + ", wxAddress=" + wxAddress + ", phoneNum=" + phoneNum + ", loginCount=" + loginCount
				+ ", remark=" + remark + ", state=" + state + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getOpenId() {
		return openId;
	}
	public void setOpenId(int openId) {
		this.openId = openId;
	}
	public String getWxnickName() {
		return wxnickName;
	}
	public void setWxnickName(String wxnickName) {
		this.wxnickName = wxnickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getWxAddress() {
		return wxAddress;
	}
	public void setWxAddress(String wxAddress) {
		this.wxAddress = wxAddress;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
