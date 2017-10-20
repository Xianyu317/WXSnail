package com.snail.model;

import java.math.BigDecimal;
import java.util.Date;

import com.snail.model.base.BaseDTO;

public class GoodsDetail extends BaseDTO {
	
	private int goodsId;
	private String goodsName;
	private BigDecimal goodsPricel;
	private int typeId;
	private int StockSize;
	private int company;
	private int integral;
	private String simpleDes;
	private String detailDes;
	private String masterImg;
	private String slaveImg;
	private int carryFare;
	private String carryFareDes;
	private String keyword;
	private String remark;
	private int state;
	private Date addedTime;
	private Date createTime;
	private Date updateTime;
	
	public Date getAddedTime() {
		return addedTime;
	}
	public void setAddedTime(Date addedTime) {
		this.addedTime = addedTime;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public BigDecimal getGoodsPricel() {
		return goodsPricel;
	}
	public void setGoodsPricel(BigDecimal goodsPricel) {
		this.goodsPricel = goodsPricel;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getStockSize() {
		return StockSize;
	}
	public void setStockSize(int stockSize) {
		StockSize = stockSize;
	}
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	public String getSimpleDes() {
		return simpleDes;
	}
	public void setSimpleDes(String simpleDes) {
		this.simpleDes = simpleDes;
	}
	public String getDetailDes() {
		return detailDes;
	}
	public void setDetailDes(String detailDes) {
		this.detailDes = detailDes;
	}
	public String getMasterImg() {
		return masterImg;
	}
	public void setMasterImg(String masterImg) {
		this.masterImg = masterImg;
	}
	public String getSlaveImg() {
		return slaveImg;
	}
	public void setSlaveImg(String slaveImg) {
		this.slaveImg = slaveImg;
	}
	public int getCarryFare() {
		return carryFare;
	}
	public void setCarryFare(int carryFare) {
		this.carryFare = carryFare;
	}
	public String getCarryFareDes() {
		return carryFareDes;
	}
	public void setCarryFareDes(String carryFareDes) {
		this.carryFareDes = carryFareDes;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
