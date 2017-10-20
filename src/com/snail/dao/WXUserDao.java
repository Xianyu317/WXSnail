package com.snail.dao;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;

import com.snail.dao.base.BaseDao;
import com.snail.model.WXUser;
import com.snail.util.DbUtil;

public class WXUserDao extends BaseDao {
	
	 QueryRunner qR = new QueryRunner();
	 
	 /**
	  * CREATE TABLE `wx_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` char(32) NOT NULL DEFAULT '' COMMENT '微信OpenID',
  `wxnick_name` varchar(50) DEFAULT NULL COMMENT '微信昵称',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实名字',
  `wx_address` varchar(100) DEFAULT NULL COMMENT '微信地址',
  `phone_num` char(11) DEFAULT NULL COMMENT '手机号码',
  `login_count` int(11) NOT NULL DEFAULT '1' COMMENT '登录次数',
  `remak` varchar(100) DEFAULT NULL COMMENT '备注',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态:0禁用，1正常',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
	  * @param user
	  * @return
	  */
	 public boolean addWXUser(WXUser user) {
	        boolean result = false;
	        Connection connection = null;

	        try {
	            connection = DbUtil.getConnection();
	            String sql = "INSERT INTO wx_user (open_id,wxnick_name,real_name,wx_address,phone_num,login_count,remak,state) VALUES (?,?,?,?,?,?,?,?)";
	            DbUtil.beginTx(connection);
	            int isSuccess = qR.update(connection, sql, user.getOpenId(),user.getWxnickName(),user.getRealName(),user.getWxAddress(),user.getPhoneNum(),user.getLoginCount(),user.getRemark(),user.getState());
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
