package com.snail.dao;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;

import com.snail.dao.base.BaseDao;
import com.snail.model.UserPoint;
import com.snail.util.DbUtil;



public class UserPointDao extends BaseDao {
	
	 QueryRunner qR = new QueryRunner();
	 
	 /**
	  * CREATE TABLE `user_point` (
  `point_Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '积分ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `point_value` int(11) NOT NULL DEFAULT '0' COMMENT '积分值',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态:0:禁用，1可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`point_Id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分表';
	  * @param up
	  * @return
	  */
	 public boolean addUserPoint(UserPoint userPoint) {
	        boolean result = false;
	        Connection connection = null;

	        try {
	            connection = DbUtil.getConnection();
	            String sql = "INSERT INTO user_point (user_id,point_value,state) VALUES (?,?,?)";
	            DbUtil.beginTx(connection);
	            int isSuccess = qR.update(connection, sql, userPoint.getUserId(),userPoint.getPointValue(),userPoint.getState());
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
