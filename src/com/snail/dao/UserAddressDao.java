package com.snail.dao;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;

import com.snail.dao.base.BaseDao;
import com.snail.model.UserAddress;
import com.snail.util.DbUtil;

public class UserAddressDao extends BaseDao {
	 QueryRunner qR = new QueryRunner();
	 
	 
	 /**
	  * CREATE TABLE `user_address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `address_detail` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：1-可用，0-删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户地址表';
	  * @param userPoint
	  * @return
	  */
	 public boolean addUserAddress(UserAddress address) {
	        boolean result = false;
	        Connection connection = null;

	        try {
	            connection = DbUtil.getConnection();
	            String sql = "INSERT INTO user_address (user_id,address_detail,remark,state) VALUES (?,?,?)";
	            DbUtil.beginTx(connection);
	            int isSuccess = qR.update(connection, sql, address.getUserId(),address.getAddressDetail(),address.getRemark(),address.getState());
	            if (isSuccess == 1) {
	            	DbUtil.commit(connection);
	                result = true;
	            } else {
	            	DbUtil.rollback(connection);
	                result = false;
	            }
	        } catch (Exception e) {
	        	DbUtil.rollback(connection);
	            e.printStackTrace();
	        } finally {
	        	DbUtil.releaseDB(null, null, connection);
	        }
	        return result;
	    }


}
