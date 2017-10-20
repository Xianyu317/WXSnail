package com.snail.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.snail.dao.ReserveOrderDao;
import com.snail.dao.ReserveOrderDetailDao;
import com.snail.model.ReserveOrder;
import com.snail.model.ReserveOrderDetail;
import com.snail.model.WXUser;
import com.snail.util.Constant;
import com.snail.util.DbUtil;
import com.snail.util.JsonUtil;
import com.snail.util.ResponseUtil;
import com.snail.web.base.BaseHttpServlet;
import com.snail.web.vo.CommonDataVO;
import com.snail.web.vo.CommonVO;

/**
 * 预约订单
 * @author Administrator
 *
 */
public class ReserveOrderServlet extends BaseHttpServlet {
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
			log.error("ReserveOrderServlet error", e);
			commonVO = new CommonVO(Constant.RSP_FAIL, "系统异常,请稍后再试");
		} finally {
			DbUtil.releaseDB(null, null, con);
		}	
		ResponseUtil.write(response, commonVO);
	}
	
	private CommonVO add(HttpServletRequest request, HttpServletResponse response, int userId, Connection con) throws SQLException {
		String orderMoney = request.getParameter("orderMoney");
		String addressId = request.getParameter("addressId");
		String remark = request.getParameter("remark");
		// 商品ID,多个以","分隔 
		String goodsIds = request.getParameter("goodsIds");
		// 购买数量,多个以","分隔 
		String buyQuantitys = request.getParameter("buyQuantitys");
		
		if (StringUtils.isNotBlank(goodsIds) && StringUtils.isNotBlank(buyQuantitys)) {
			BigInteger orderAddressId = orderDao.addOrderAddress(addressId, userId, con);
			
			ReserveOrder order = new ReserveOrder();
			order.setUserId(userId);
			order.setOrderMoney(new BigDecimal(orderMoney));
			order.setAddressId(orderAddressId.intValue());
			order.setRemark(remark);
			order.setState(Constant.ORDER_STATUS_PROCESSING);
			BigInteger orderId = orderDao.addUserOrder(order, con);
			
			String[] goodsIdArray = goodsIds.trim().split(",");
			String[] buyQuantityArray = buyQuantitys.trim().split(",");
			for (int i = 0; i < goodsIdArray.length; i++) {
				ReserveOrderDetail detail = new ReserveOrderDetail();
				detail.setOrderId(orderId.intValue());
				detail.setGoodsId(Integer.parseInt(goodsIdArray[i])); 
				detail.setBuyQuantity(Integer.parseInt(buyQuantityArray[i]));
				detailDao.addUserOrderDetail(detail, con);
			}
			return new CommonVO();
		}
		return new CommonVO(Constant.RSP_FAIL, "创建订单失败");
	}
	
	private CommonVO update(HttpServletRequest request, HttpServletResponse response, int userId, Connection con) throws Exception {
		String detailId = request.getParameter("detailId");
		String buyQuantity = request.getParameter("buyQuantity");
		if (StringUtils.isBlank(detailId) && StringUtils.isBlank(buyQuantity)) {
			return new CommonVO(Constant.RSP_FAIL, "订单详情号和购买数量不能为空");
		}
		detailDao.updateBuyQuantityByDetailId(con, buyQuantity, detailId);
		return new CommonVO();
	}
	
	private CommonVO query(HttpServletRequest request, HttpServletResponse response, int userId, Connection con) throws SQLException {
		String orderId = request.getParameter("orderId");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		JSONObject result = new JSONObject();
		JSONArray list = JsonUtil.formatRsToJsonArray(orderDao.queryList(userId, orderId, page, rows, con));
		int total = orderDao.queryListCount(userId, orderId, con);
		result.put("rows", list);
		result.put("total", total);
		log.info("result===="+result);
		
		return new CommonDataVO<JSONObject>(result);
	}
	
	private CommonVO delete(HttpServletRequest request, HttpServletResponse response, int userId, Connection con) throws SQLException {
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
