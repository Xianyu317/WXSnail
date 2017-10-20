package com.snail.model;

import java.util.Date;

import com.snail.model.base.BaseDTO;

public class ScrollGraph extends BaseDTO {
	
	private int graphId;
	private String graphPath;
	private int orderNum;
	private String remark;
	private int state;
	private Date createTime;
	private Date updateTime;
	
	public ScrollGraph() {
		super();
	}
	public ScrollGraph(int graphId, String graphPath, int orderNum, String remark, int state, Date createTime,
			Date updateTime) {
		super();
		this.graphId = graphId;
		this.graphPath = graphPath;
		this.orderNum = orderNum;
		this.remark = remark;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public int getGraphId() {
		return graphId;
	}
	public void setGraphId(int graphId) {
		this.graphId = graphId;
	}
	public String getGraphPath() {
		return graphPath;
	}
	public void setGraphPath(String graphPath) {
		this.graphPath = graphPath;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
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
