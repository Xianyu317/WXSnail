package com.snail.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.snail.dao.ReserveOrderDao;
import com.snail.dao.ReserveOrderDetailDao;
import com.snail.util.Constant;
import com.snail.util.DbUtil;
import com.snail.util.JsonUtil;
import com.snail.util.ResponseUtil;
import com.snail.web.base.BaseHttpServlet;
import com.snail.web.vo.CommonDataVO;
import com.snail.web.vo.CommonVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 预约订单
 * @author Administrator
 *
 */
public class ReserveOrderManageServlet extends BaseHttpServlet {
	private static final long serialVersionUID = 2882444180913411510L;
	
	ReserveOrderDao orderDao = new ReserveOrderDao();
	ReserveOrderDetailDao detailDao = new ReserveOrderDetailDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		Connection con = null;
		CommonVO commonVO = null;
		try {
			con = DbUtil.getConnection();
			
			if("query".equals(action)){
				commonVO = queryByManage(request, response, con);
			} else if("delete".equals(action)){
				commonVO = deleteByManage(request, response, con);
			} 
		} catch (Exception e) {
			log.error("ReserveOrderServlet error", e);
			commonVO = new CommonVO(Constant.RSP_FAIL, "系统异常,请稍后再试");
		} finally {
			DbUtil.releaseDB(null, null, con);
		}	
		ResponseUtil.write(response, commonVO);
	}
	
	private CommonVO queryByManage(HttpServletRequest request, HttpServletResponse response, Connection con) throws SQLException {
		String goodsName = request.getParameter("goodsName");
		String userId = request.getParameter("userId");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		JSONObject result = new JSONObject();
		JSONArray list = JsonUtil.formatRsToJsonArray(orderDao.queryByManage(userId, goodsName, page, rows, con));
		int total = orderDao.queryByManageCount(userId, goodsName, con);
		result.put("rows", list);
		result.put("total", total);
		log.info("result===="+result);
		
		return new CommonDataVO<JSONObject>(result);
	}
	
	private CommonVO deleteByManage(HttpServletRequest request, HttpServletResponse response, Connection con) throws SQLException {
		String orderId = request.getParameter("orderId");
		String detailId = request.getParameter("detailId");
		if (StringUtils.isBlank(orderId) && StringUtils.isBlank(detailId)) {
			return new CommonVO(Constant.RSP_FAIL, "订单号和订单详情号为空");
		}
		
		if (StringUtils.isBlank(orderId)) {
			orderDao.delete(con, orderId);
			detailDao.delete(con, orderId);
		} else {
			detailDao.deleteByDetailId(con, detailId);
		}
		
		return new CommonVO();
	}
}
