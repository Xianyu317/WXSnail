package com.snail.dao;

import java.sql.Connection;

import com.snail.dao.base.BaseDao;
import com.snail.model.PointDetail;
import com.snail.util.DbUtil;

public class PointDetailDao extends BaseDao {
	
	 /**
	  * CREATE TABLE `point_detail` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `point_id` int(11) DEFAULT NULL COMMENT '积分ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `order_id` int(11) DEFAULT NULL COMMENT '积分来源订单号',
  `point_value` int(11) NOT NULL DEFAULT '0' COMMENT '积分值',
  `remark` varchar(50) DEFAULT NULL COMMENT '积分来源备注',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态:0无效，1有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分明细';
	  * @param detail
	  * @return
	  */
	 public boolean addPointDetail(PointDetail detail) {
	        boolean result = false;
	        Connection connection = null;

	        try {
	            connection = DbUtil.getConnection();
	            String sql = "INSERT INTO point_detail (point_id,user_id,order_id,point_value,remark,state) VALUES (?,?,?,?,?,?)";
	            DbUtil.beginTx(connection);
	            int isSuccess = qR.update(connection, sql, detail.getPointId(),detail.getUserId(),detail.getOrderId(),detail.getPointValue(),detail.getRemark(),detail.getState());
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
