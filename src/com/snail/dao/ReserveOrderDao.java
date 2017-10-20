package com.snail.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;

import com.snail.dao.base.BaseDao;
import com.snail.model.PageBean;
import com.snail.model.ReserveOrder;
import com.snail.util.DbUtil;
import com.snail.util.StringUtil;

public class ReserveOrderDao extends BaseDao {
	 
	/**
	 * CREATE TABLE `reserve_order` (
		  `order_id` int(11) NOT NULL AUTO_INCREMENT,
		  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
		  `order_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
		  `address_id` int(11) DEFAULT NULL COMMENT '收货地址ID',
		  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
		  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0派送成功，1未派送，2派送中，3派送失败，4、取消订单',
		  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
		  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
		  PRIMARY KEY (`order_id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约订单表';
	 */
	 
	public BigInteger addUserOrder(ReserveOrder order, Connection connection) throws SQLException {
		BigInteger id = null;

		String sql = "INSERT INTO reserve_order (user_id,order_money,address_id,remark,state) VALUES (?,?,?,?,?)";
		DbUtil.beginTx(connection);
		int isSuccess = qR.update(connection, sql, order.getUserId(), order.getOrderMoney(), order.getAddressId(), order.getRemark(), order.getState());
		if (isSuccess == 1) {
			DbUtil.commit(connection);
			// 获取新增记录的自增主键
			id = qR.query(connection, "SELECT LAST_INSERT_ID()", new ScalarHandler<BigInteger>(1));
		} else {
			DbUtil.rollback(connection);
		}
		return id;
	}
	
	public BigInteger addOrderAddress(String addressId, int userId, Connection connection) throws SQLException {
		BigInteger id = null;

		String sql = "INSERT INTO order_address(user_id,name,mobile,address_detail,remark) SELECT user_id,name,mobile,address_detail,remark FROM user_address WHERE user_id = ? AND address_id = ?";
		DbUtil.beginTx(connection);
		int isSuccess = qR.update(connection, sql, userId, addressId);
		if (isSuccess == 1) {
			DbUtil.commit(connection);
			// 获取新增记录的自增主键
			id = qR.query(connection, "SELECT LAST_INSERT_ID()", new ScalarHandler<BigInteger>(1));
		} else {
			DbUtil.rollback(connection);
		}
		return id;
	}
	
	
	public int delete(Connection con, String orderId) throws SQLException {
		String sql = "delete from reserve_order where order_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, orderId);
		return pstmt.executeUpdate();
	}
	
	public ResultSet queryList(int userId, String orderId, String page, String rows, Connection con) throws SQLException {
		PageBean pageBean = new PageBean(StringUtils.isNotBlank(page) ? Integer.parseInt(page) : 1, 
										 StringUtils.isNotBlank(rows) ? Integer.parseInt(rows) : 10);
		StringBuffer sb = new StringBuffer("SELECT o.*,d.*,g.*,a.* FROM reserve_order o LEFT JOIN reserve_order_detail d ON o.order_id = d.order_id "
				+ "LEFT JOIN goods_detail g ON d.goods_id = g.goods_id LEFT JOIN user_address a ON o.address_id = a.address_id WHERE o.user_id = "+ userId);
		
		if (StringUtil.isNotEmpty(orderId)) {
			sb.append(" AND o.order_id = "+ orderId);
		}
		
		sb.append(" ORDER BY o.create_time DESC");
		
		if(pageBean != null){
			sb.append(" limit "+ pageBean.getStart() +","+ pageBean.getRows());
		}
		
		log.info(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet resultSet = pstmt.executeQuery();
		return resultSet;
	}
	
	public int queryListCount(int userId, String orderId, Connection con) throws SQLException {
		StringBuffer sb = new StringBuffer("SELECT count(1) as total FROM reserve_order o LEFT JOIN reserve_order_detail d ON o.order_id = d.order_id "
				+ "LEFT JOIN goods_detail g ON d.goods_id = g.goods_id LEFT JOIN user_address a ON o.address_id = a.address_id WHERE o.user_id = "+ userId);
		
		if (StringUtil.isNotEmpty(orderId)) {
			sb.append(" AND o.order_id = "+ orderId);
		}
		
		log.info(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		}
		return 0;
	}
	
	
	
	
	
	
	public ResultSet queryByManage(String userId, String goodsName, String page, String rows, Connection con) throws SQLException {
		PageBean pageBean = new PageBean(StringUtils.isNotBlank(page) ? Integer.parseInt(page) : 1, 
				StringUtils.isNotBlank(rows) ? Integer.parseInt(rows) : 10);
		StringBuffer sb = new StringBuffer("SELECT o.*,d.*,g.*,a.* FROM reserve_order o LEFT JOIN reserve_order_detail d ON o.order_id = d.order_id "
				+ "LEFT JOIN goods_detail g ON d.goods_id = g.goods_id LEFT JOIN user_address a ON o.address_id = a.address_id WHERE 1=1 ");
		
		if (StringUtil.isNotEmpty(userId)) {
			sb.append(" AND o.user_id = "+ userId);
		}
		if (StringUtil.isNotEmpty(goodsName)) {
			sb.append(" AND g.goods_name like %'"+ goodsName +"'%");
		}
		sb.append(" ORDER BY o.create_time DESC");
		if(pageBean != null){
			sb.append(" limit "+ pageBean.getStart() +","+ pageBean.getRows());
		}
		
		log.info(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet resultSet = pstmt.executeQuery();
		return resultSet;
	}
	
	public int queryByManageCount(String userId, String goodsName, Connection con) throws SQLException {
		StringBuffer sb = new StringBuffer("SELECT o.*,d.*,g.*,a.* FROM reserve_order o LEFT JOIN reserve_order_detail d ON o.order_id = d.order_id "
				+ "LEFT JOIN goods_detail g ON d.goods_id = g.goods_id LEFT JOIN user_address a ON o.address_id = a.address_id WHERE 1=1 ");
		
		if (StringUtil.isNotEmpty(userId)) {
			sb.append(" AND o.user_id = "+ userId);
		}
		if (StringUtil.isNotEmpty(goodsName)) {
			sb.append(" AND g.goods_name like %'"+ goodsName +"'%");
		}
		
		log.info(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		}
		return 0;
	}
	 
}
