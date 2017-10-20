package com.snail.web.vo;

public class CommonDataVO<T> extends CommonVO {
	
	/**
	 * 业务数据
	 */
	private T data;
	
	public CommonDataVO(){}
	
	public CommonDataVO(T data) {
		this.data = data;
	}
	
	public CommonDataVO(String responseCode){
    	this.responseCode=responseCode;
    }
    
    public CommonDataVO(String responseCode,String responseMsg){
    	this.responseCode=responseCode;
    	this.responseMsg=responseMsg;
    }
    
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
