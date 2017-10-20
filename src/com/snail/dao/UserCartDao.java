package com.snail.dao;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;




import com.snail.dao.base.BaseDao;
import com.snail.model.UserCart;
import com.snail.util.DbUtil;

public class UserCartDao extends BaseDao {
	 QueryRunner qR = new QueryRunner();
	 
	 /**
	  * CREATE TABLE `user_cart` (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `quantity` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品数量',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0，删除，1正常',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车';
	  * @param cart
	  * @return
	  */
	 public boolean addUserCart(UserCart cart) {
	        boolean result = false;
	        Connection connection = null;
	        try {
	            connection = DbUtil.getConnection();
	            String sql = "INSERT INTO user_cart (user_id,goods_id,quantity,remark,state) VALUES (?,?,?,?,?)";
	            DbUtil.beginTx(connection);
	            int isSuccess = qR.update(connection, sql, cart.getUserId(),cart.getGoodsId(),cart.getQuantity(),cart.getRemark(),cart.getState());
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
