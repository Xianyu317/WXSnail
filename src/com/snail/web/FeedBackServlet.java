package com.snail.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.snail.dao.FeedBackDao;
import com.snail.model.FeedBack;
import com.snail.model.PageBean;
import com.snail.model.SysUser;
import com.snail.util.DbUtil;
import com.snail.util.JsonUtil;
import com.snail.util.ResponseUtil;
import com.snail.util.StringUtil;
import com.snail.web.base.BaseHttpServlet;

public class FeedBackServlet extends BaseHttpServlet {
	DbUtil dbUtil = new DbUtil();
	FeedBackDao feedBackDao = new FeedBackDao();
	private static final long serialVersionUID = 1L;

	public FeedBackServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");

		if ("list".equals(action)) {
			list(request, response);
		} else if ("save".equals(action)) {
			save(request, response);
		} else if ("delete".equals(action)) {
			
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		FeedBack feedBack = new FeedBack();
		String feedContent = request.getParameter("feedcontent");

		if (StringUtil.isNotEmpty(feedContent)) {
			feedBack.setFeedContent(feedContent);
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(feedBackDao.feedBackList(con, pageBean, feedBack));
	
			int total = feedBackDao.feedBackCount(con, feedBack);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.releaseDB(null, null, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String roleId = request.getParameter("roleId");
		String userDescription = request.getParameter("userDescription");
		String userId = request.getParameter("userId");
		SysUser user = new SysUser(userName, password, Integer.parseInt(roleId), userDescription);
		if (StringUtil.isNotEmpty(userId)) {
			user.setUserId(Integer.parseInt(userId));
		}
		Connection con = null;
		try {
			JSONObject result = new JSONObject();
			con = dbUtil.getConnection();
			int saveNums = 0;
			if (StringUtil.isNotEmpty(userId)) {
				
			} else {
				
			}
			if (saveNums == -1) {
				result.put("success", true);
				result.put("errorMsg", "此用户名已经存在");
			} else if (saveNums == 0) {
				result.put("success", true);
				result.put("errorMsg", "保存失败");
			} else {
				result.put("success", true);
			}
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.releaseDB(null, null, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
