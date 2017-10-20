package com.snail.model.base;

import com.alibaba.fastjson.JSONObject;

public class BaseDTO {
	
	@Override
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}

