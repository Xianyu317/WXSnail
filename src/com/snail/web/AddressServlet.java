package com.snail.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.snail.dao.AddressDao;
import com.snail.model.UserAddress;
import com.snail.model.WXUser;
import com.snail.util.Constant;
import com.snail.util.DbUtil;
import com.snail.util.JsonUtil;
import com.snail.util.ResponseUtil;
import com.snail.web.base.BaseHttpServlet;
import com.snail.web.vo.CommonDataVO;
import com.snail.web.vo.CommonVO;

public class AddressServlet extends BaseHttpServlet {
	private static final long serialVersionUID = -4324564609212188883L;
	
	AddressDao addressDao = new AddressDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		HttpSession session = request.getSession();
		WXUser user = (WXUser) session.getAttribute(Constant.SESSION_USER);
		int userId = user.getUserId();
		
		Connection con = null;
		CommonVO commonVO = null;
		try {
			con = DbUtil.getConnection();
			
			if ("add".equals(action)) {
				commonVO = add(request, response, userId, con);			
			} else if("update".equals(action)){
				commonVO = update(request, response, userId, con);
			} else if("query".equals(action)){
				commonVO = query(request, response, userId, con);
			} else if("delete".equals(action)){
				commonVO = delete(request, response, userId, con);
			} 
		} catch (Exception e) {
			log.error("AddressServlet error", e);
			commonVO = new CommonVO(Constant.RSP_FAIL, "系统异常,请稍后再试");
		} finally {
			DbUtil.releaseDB(null, null, con);
		}	
		ResponseUtil.write(response, commonVO);
	}
	
	private CommonVO add(HttpServletRequest request, HttpServletResponse response, int userId, Connection con) throws SQLException {
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String addressDetail = request.getParameter("addressDetail");
		String remark = request.getParameter("remark");
		
		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(addressDetail)) {
			UserAddress address = new UserAddress();
			address.setUserId(userId);
			address.setName(name);
			address.setMobile(mobile);
			address.setAddressDetail(addressDetail);
			address.setRemark(remark);
			addressDao.addUserAddress(address, con);
			return new CommonVO();
		}
		return new CommonVO(Constant.RSP_FAIL, "创建收货地址失败");
	}
	
	private CommonVO update(HttpServletRequest request, HttpServletResponse response, int userId, Connection con) throws Exception {
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String addressDetail = request.getParameter("addressDetail");
		String remark = request.getParameter("remark");
		String addressId = request.getParameter("addressId");
		
		if (StringUtils.isNotBlank(addressId)) {
			UserAddress address = new UserAddress();
			address.setUserId(userId);
			address.setAddressId(Integer.parseInt(addressId));
			address.setName(name);
			address.setMobile(mobile);
			address.setAddressDetail(addressDetail);
			address.setRemark(remark);
			addressDao.updateUserAddressByAddressId(con, address);
			return new CommonVO();
		}
		return new CommonVO(Constant.RSP_FAIL, "更新收货地址失败");
	}
	
	private CommonVO query(HttpServletRequest request, HttpServletResponse response, int userId, Connection con) throws SQLException {
		String addressId = request.getParameter("addressId");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		JSONObject result = new JSONObject();
		JSONArray list = JsonUtil.formatRsToJsonArray(addressDao.queryList(userId, addressId, page, rows, con));
		result.put("rows", list);
		log.info("result===="+result);
		return new CommonDataVO<JSONObject>(result);
	}
	
	private CommonVO delete(HttpServletRequest request, HttpServletResponse response, int userId, Connection con) throws SQLException {
		String addressId = request.getParameter("addressId");
		if (StringUtils.isBlank(addressId)) {
			return new CommonVO(Constant.RSP_FAIL, "收货地址ID不能为空");
		}
		addressDao.delete(con, addressId, userId);
		return new CommonVO();
	}
}
