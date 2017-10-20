package com.snail.util;

public class Constant {
	
	/** 返回状态码：0=成功  */
	public static final String RSP_SUCCESS = "0";
	/** 返回状态码：1=失败 */
	public static final String RSP_FAIL = "1";
	
	/** 登录user常量  */
	public static final String SESSION_USER = "user";
	 
	/** 状态： 0派送成功 */
	public static final int ORDER_STATUS_SUCCESS = 0;
	/** 状态：1未派送  */
	public static final int ORDER_STATUS_OT_DONE = 1;
	/** 状态：2派送中  */
	public static final int ORDER_STATUS_PROCESSING = 2;
	/** 状态：3派送失败 */
	public static final int ORDER_STATUS_FAIL = 3;
	/** 状态：4取消订单 */
	public static final int ORDER_STATUS_CANCEL = 4;
}
