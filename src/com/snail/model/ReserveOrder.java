package com.snail.model;

import java.math.BigDecimal;
import java.util.Date;

import com.snail.model.base.BaseDTO;

public class ReserveOrder extends BaseDTO {

	private int orderId;
	private int userId;
	private BigDecimal orderMoney;
	private int addressId;
	private String remark;
	// 状态：0派送成功，1未派送，2派送中，3派送失败，4、取消订单
	private int state;
	private Date createTime;
	private Date updateTime;
	
	
	
	public ReserveOrder() {
		super();
	}
	public ReserveOrder(int orderId, int userId, BigDecimal orderMoney, int addressId, String remark, int state,
			Date createTime, Date updateTime) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.orderMoney = orderMoney;
		this.addressId = addressId;
		this.remark = remark;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public BigDecimal getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
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
