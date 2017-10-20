package com.snail.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserCart {
	
	private int cartId;
	private int userId;
	private int goodsId;
	private BigDecimal quantity;
	private String remark;
	private int state;
	private Date createTime;
	private Date updateTime;
	
	
	
	public UserCart() {
		super();
	}
	public UserCart(int cartId, int userId, int goodsId, BigDecimal quantity, String remark, int state, Date createTime,
			Date updateTime) {
		super();
		this.cartId = cartId;
		this.userId = userId;
		this.goodsId = goodsId;
		this.quantity = quantity;
		this.remark = remark;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "UserCart [cartId=" + cartId + ", userId=" + userId + ", goodsId=" + goodsId + ", quantity=" + quantity
				+ ", remark=" + remark + ", state=" + state + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
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
