package com.snail.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.snail.model.AppUser;
import com.snail.util.HttpUtil;
import com.snail.util.ResponseUtil;
import com.snail.web.base.BaseHttpServlet;
import com.snail.web.vo.CommonDataVO;

import net.sf.json.JSONObject;

public class LoginServlet extends BaseHttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("login".equals(action)){
			login(request, response);			
		}
		
	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		String jsCode=request.getParameter("code");
		String appId=request.getParameter("appId");
		String appSecret=request.getParameter("appSecret");
		CommonDataVO<AppUser> vo = new CommonDataVO<AppUser>();
		
	    if (jsCode == null || "".equals(jsCode)) {
	      request.setAttribute("error", "缺少必要参数！");
	      return;
	    } else {
	      if (appId == null || "".equals(appId) || appSecret == null || "".equals(appSecret)) {
	    	  request.setAttribute("error", "缺少必要参数！");
		      return;
	      } else {
	        String url = "https://api.weixin.qq.com/sns/jscode2session";
	        String httpUrl = url + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + jsCode
	            + "&grant_type=authorization_code";
	        String ret = HttpUtil.httpGet(httpUrl, null);
	        log.info("微信返回的结果 {}"+ret);
	        if (ret == null || "".equals(ret)) {
	        	log.info("请求微信异常"+ret);
	        	request.setAttribute("error", "请求微信异常！");
			    return;
	        } else {
	          JSONObject obj = JSONObject.fromObject(ret);
	          if (obj.containsKey("errcode")) {
	            String errcode = obj.get("errcode").toString();
	            log.info("微信返回的错误码{}"+errcode);
	        	request.setAttribute("error", "微信返回的错误码{}"+errcode);
			    return;
	          } else if (obj.containsKey("session_key")) {
	            log.info("调微信成功");
	            // 开始处理userInfo
	            String openId = obj.get("openid").toString();
	            System.out.println("openId==" + openId);
	            // 先查询openId存在不存在，存在不入库，不存在就入库
//	            List<Record> memberList = tbMemberService.selectMember(tbMember);
//	            if (memberList != null && memberList.size() > 0) {
//	            	log.info("openId已经存在，不需要插入");
//	            } else {
//	              JSONObject rawDataJson = obj.getJSONObject("userInfo");
//	              String nickName = rawDataJson.getString("nickName");
//	              String avatarUrl = rawDataJson.getString("avatarUrl");
//	              String gender = rawDataJson.getString("gender");
//	              String province = rawDataJson.getString("province");
//	              String city = rawDataJson.getString("city");
//	              String country = rawDataJson.getString("country");
//	              log.info("openId不存在，插入数据库");
//	            }
	            AppUser user = new AppUser();
	            user.setOpenId(openId);
	            session.setAttribute("appUser", user);
	            // (1) 获得sessionkey
	            String sessionKey = obj.get("session_key").toString();
	            user.setSessionKey(sessionKey);
	            String unionId = obj.get("unionid").toString();
	            user.setUnionId(unionId);
	            
	            log.info("sessionKey==" + sessionKey+";"+"openId==" + openId+";unionid"+unionId);
	            // (2) 得到sessionkey以后存到缓存，key值采用不会重复的uuid
	            String rsession = UUID.randomUUID().toString();
	            vo.setData(user);
	            
	          }
	 
	        }
	      }
	    }
	    if(vo.getData() == null){
	    	vo.setResponseCode("1");
	    	vo.setResponseMsg("获取微信用户信息失败，登陆失败！");
	    }
	    ResponseUtil.write(response, vo);
	}
	
}
