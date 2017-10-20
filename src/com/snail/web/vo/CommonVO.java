package com.snail.web.vo;

import com.alibaba.fastjson.JSONObject;

public class CommonVO {
	protected String responseCode = "0";
	    
    protected String responseMsg  ;
    
    public CommonVO(){}
    
    public CommonVO(String responseCode){
    	this.responseCode=responseCode;
    }
    
    public CommonVO(String responseCode,String responseMsg){
    	this.responseCode=responseCode;
    	this.responseMsg=responseMsg;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
    
    @Override
    public String toString(){
    	return JSONObject.toJSONString(this);
    }
}
