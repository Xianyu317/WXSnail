package com.snail.model;

import java.util.Date;

import com.snail.model.base.BaseDTO;

public class ReserveOrderDetail extends BaseDTO {
	private int detailId;
	private int orderId;
	private int goodsId;
	private int buyQuantity;
	private String remark;
	private Date createTime;
	private Date updateTime;
	
	public ReserveOrderDetail() {
		super();
	}
	
	public ReserveOrderDetail(int detailId, int orderId, int goodsId, String remark, 
			Date createTime, Date updateTime) {
		super();
		this.detailId = detailId;
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	
	public int getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(int buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public int getDetailId() {
		return detailId;
	}
	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
