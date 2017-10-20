package com.snail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.snail.dao.base.BaseDao;
import com.snail.model.ReserveOrderDetail;
import com.snail.util.DbUtil;

public class ReserveOrderDetailDao extends BaseDao {

	/**
	 * CREATE TABLE `reserve_order_detail` ( `detail_id` int(11) NOT NULL
	 * AUTO_INCREMENT COMMENT '详细订单ID', `order_id` int(11) DEFAULT NULL COMMENT
	 * '预约订单ID', `goods_id` int(11) DEFAULT NULL COMMENT '商品ID', `remark`
	 * varchar(100) DEFAULT NULL COMMENT '描述', `state` tinyint(1) NOT NULL
	 * DEFAULT '1' COMMENT '状态：1正常，0删除', `create_time` timestamp NOT NULL
	 * DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', `update_time` timestamp NOT
	 * NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT
	 * '修改时间', PRIMARY KEY (`detail_id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8
	 * COMMENT='预约订单明细表';
	 * 
	 * @param order
	 * @return
	 */
	public boolean addUserOrderDetail(ReserveOrderDetail order, Connection connection) throws SQLException {
		boolean result = false;
		String sql = "INSERT INTO reserve_order_detail (order_id,goods_id,buy_quantity,remark) VALUES (?,?,?,?)";
		DbUtil.beginTx(connection);
		int isSuccess = qR.update(connection, sql, order.getOrderId(), order.getGoodsId(), order.getBuyQuantity(), order.getRemark());
		if (isSuccess == 1) {
			DbUtil.commit(connection);
			result = true;
		} else {
			DbUtil.rollback(connection);
			result = false;
		}
		return result;
	}
	
	public int delete(Connection con, String orderId) throws SQLException {
		String sql = "delete from reserve_order_detail where order_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, orderId);
		return pstmt.executeUpdate();
	}
	
	public int deleteByDetailId(Connection con, String detailId) throws SQLException {
		String sql = "delete from reserve_order_detail where detail_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, detailId);
		return pstmt.executeUpdate();
	}
	
	public int updateBuyQuantityByDetailId(Connection con, String buyQuantity, String detailId) throws Exception{
		String sql="update reserve_order_detail set buy_quantity = ? where detail_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, buyQuantity);
		pstmt.setString(2, detailId);
		return pstmt.executeUpdate();
	}
}
