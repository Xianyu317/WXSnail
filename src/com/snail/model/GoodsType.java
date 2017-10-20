package com.snail.model;

import java.util.Date;

public class GoodsType {
	private int typeId;
	private int typeTopid;
	private int isParent;
	private String typeName;
	private String remak;
	private int state;
	private Date createTime;
	private Date updateTime;
	
	
	public GoodsType() {
		super();
	}
	public GoodsType(int typeId, int typeTopid, int isParent, String typeName, String remak, int state, Date createTime,
			Date updateTime) {
		super();
		this.typeId = typeId;
		this.typeTopid = typeTopid;
		this.isParent = isParent;
		this.typeName = typeName;
		this.remak = remak;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "GoodsType [typeId=" + typeId + ", typeTopid=" + typeTopid + ", isParent=" + isParent + ", typeName="
				+ typeName + ", remak=" + remak + ", state=" + state + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getTypeTopid() {
		return typeTopid;
	}
	public void setTypeTopid(int typeTopid) {
		this.typeTopid = typeTopid;
	}
	public int getIsParent() {
		return isParent;
	}
	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getRemak() {
		return remak;
	}
	public void setRemak(String remak) {
		this.remak = remak;
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
