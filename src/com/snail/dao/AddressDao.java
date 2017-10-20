package com.snail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.snail.dao.base.BaseDao;
import com.snail.model.PageBean;
import com.snail.model.UserAddress;
import com.snail.util.DbUtil;
import com.snail.util.StringUtil;

public class AddressDao extends BaseDao {

	/**
	 * CREATE TABLE `user_address` (
		  `address_id` int(11) NOT NULL AUTO_INCREMENT,
		  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
		  `name` varchar(20) DEFAULT NULL COMMENT '收货人姓名',
		  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
		  `address_detail` varchar(255) DEFAULT NULL COMMENT '详细地址',
		  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
		  `state` int(1) NOT NULL DEFAULT '1' COMMENT '状态：1-可用，0-删除',
		  `isDefault` int(1) NOT NULL DEFAULT '0' COMMENT '默认地址: 0否，1是',
		  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
		  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
		  PRIMARY KEY (`address_id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户地址表';
	 */
	
	public ResultSet queryList(int userId, String addressId, String page, String rows, Connection con) throws SQLException {
		PageBean pageBean = new PageBean(StringUtils.isNotBlank(page) ? Integer.parseInt(page) : 1, 
										 StringUtils.isNotBlank(rows) ? Integer.parseInt(rows) : 10);
		
		StringBuffer sb = new StringBuffer("SELECT * FROM user_address WHERE user_id = "+ userId);
		
		if (StringUtil.isNotEmpty(addressId)) {
			sb.append(" And address_id = "+ addressId);
		}
		
		sb.append(" ORDER BY create_time DESC");
		
		if(pageBean != null){
			sb.append(" limit "+ pageBean.getStart() +","+ pageBean.getRows());
		}
		
		log.info(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet resultSet = pstmt.executeQuery();
		return resultSet;
	}
	
	public boolean addUserAddress(UserAddress address, Connection connection) throws SQLException {
		boolean result = false;
		String sql = "INSERT INTO user_address (user_id, name, mobile, address_detail, remark) VALUES (?, ?, ?, ?, ?)";
		DbUtil.beginTx(connection);
		int isSuccess = qR.update(connection, sql, address.getUserId(), address.getName(), address.getMobile(), address.getAddressDetail(), address.getRemark());
		if (isSuccess == 1) {
			DbUtil.commit(connection);
			result = true;
		} else {
			DbUtil.rollback(connection);
			result = false;
		}
		return result;
	}
	
	public int delete(Connection con, String addressId, int userId) throws SQLException {
		String sql = "delete from user_address where address_id = ? and user_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, addressId);
		pstmt.setInt(2, userId);
		return pstmt.executeUpdate();
	}
	
	public int updateUserAddressByAddressId(Connection con, UserAddress address) throws Exception{
		String sql = "update user_address set name = ?, mobile = ?, address_detail = ?, remark = ? where address_id = ? and user_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, address.getName());
		pstmt.setString(2, address.getMobile());
		pstmt.setString(3, address.getAddressDetail());
		pstmt.setString(4, address.getRemark());
		pstmt.setInt(5, address.getAddressId());
		pstmt.setInt(6, address.getUserId());
		return pstmt.executeUpdate();
	}
}
