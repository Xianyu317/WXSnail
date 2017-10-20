package com.snail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.snail.dao.base.BaseDao;
import com.snail.model.GoodsDetail;
import com.snail.model.PageBean;
import com.snail.util.DateUtil;
import com.snail.util.DbUtil;
import com.snail.util.StringUtil;

public class GoodsDetailDao extends BaseDao {
	 
	/**
	 * 
	 *CREATE TABLE `goods_detail` (
	  `goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
	  `goods_name` varchar(50) NOT NULL DEFAULT '' COMMENT '商品名称',
	  `goods_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
	  `type_id` int(11) DEFAULT NULL COMMENT '类别ID',
	  `stock_size` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
	  `company` int(1) NOT NULL DEFAULT '1' COMMENT '单位:1:斤，2：千克，3：两，4：克',
	  `integral` int(5) DEFAULT NULL COMMENT '积分',
	  `simple_des` varchar(100) NOT NULL DEFAULT '' COMMENT '简述',
	  `detail_des` varchar(255) DEFAULT NULL COMMENT '详述',
	  `master_img` varchar(100) DEFAULT NULL COMMENT '主图路径',
	  `slave_img` varchar(100) DEFAULT NULL COMMENT '辅图路径',
	  `carry_fare` smallint(6) DEFAULT '0' COMMENT '运费',
	  `carry_fare_des` varchar(100) DEFAULT '' COMMENT '运费描述',
	  `keyword` varchar(50) DEFAULT NULL COMMENT '关键字',
	  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
	  `state` int(3) NOT NULL DEFAULT '1' COMMENT '状态：1正常，0删除，2下架',
	  `added_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上架时间',
	  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	  PRIMARY KEY (`goods_id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';
	 */
	
	public ResultSet queryList(String queryKey, String typeId,String addedTime, String page, String rows, Connection con) throws SQLException {
		PageBean pageBean = new PageBean(StringUtils.isNotBlank(page) ? Integer.parseInt(page) : 1, 
										 StringUtils.isNotBlank(rows) ? Integer.parseInt(rows) : 10);
		
		StringBuffer sb = new StringBuffer("SELECT * FROM goods_detail where 1=1 ");
		
//		if (!"2".equals(queryKey)) {
//			sb.append(" AND added_time < SYSDATE()");
//		}
		
		if ("1".equals(queryKey)) {
			if(StringUtils.isNotEmpty(typeId)){
				sb.append(" AND type_id = " + typeId);
			}else{
				sb.append(" AND type_id = 1");
			}
			
		} else if ("2".equals(queryKey)) {
			if (StringUtils.isBlank(addedTime)) {
				addedTime = DateUtil.getSysBrofeDay(1);
			} 
			
			sb.append(" AND date_format(added_time, '%Y-%m-%d') = '"+ addedTime +"'");
		} else if ("3".equals(queryKey)) {
			sb.append(" AND integral IS NOT null  ORDER BY integral DESC");
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
	
	public ResultSet query(String good_id, Connection con) throws SQLException {
		
		StringBuffer sb = new StringBuffer("SELECT * FROM goods_detail where goods_id =  " + good_id);
		
		log.info(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet resultSet = pstmt.executeQuery();
		return resultSet;
	}
	
	public int queryListCount(String queryKey, String addedTime, String page, String rows, Connection con) throws SQLException {
		StringBuffer sb = new StringBuffer("SELECT count(1) as total FROM goods_detail where 1=1 ");
		if (!"2".equals(queryKey)) {
			sb.append(" AND added_time < SYSDATE()");
		}
		
		if ("1".equals(queryKey)) {
			sb.append(" AND type_id = 1");
		} else if ("2".equals(queryKey)) {
			if (StringUtils.isBlank(addedTime)) {
				addedTime = DateUtil.getSysBrofeDay(1);
			} 
			
			sb.append(" AND date_format(added_time, '%Y-%m-%d') = '"+ addedTime +"'");
		} else if ("3".equals(queryKey)) {
			sb.append(" AND integral IS NOT null ");
		}
		
		log.info(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		}
		return 0;
	}
	 
 public boolean addGoodsDetail(GoodsDetail detail) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbUtil.getConnection();
            String sql = "INSERT INTO goods_detail (goods_name,goods_price,type_id,stock_size,company,simple_des,detail_des,master_img,slave_img,carry_fare,carry_fare_des,keyword,remark,integral,added_time) "
            		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            DbUtil.beginTx(connection);
            int isSuccess = qR.update(connection, sql, detail.getGoodsName(),detail.getGoodsPricel(),detail.getTypeId(),detail.getStockSize(),
            		detail.getCompany(),detail.getSimpleDes(),detail.getDetailDes(),detail.getMasterImg(),detail.getSlaveImg(),detail.getCarryFare(),
            		detail.getCarryFareDes(),detail.getKeyword(),detail.getRemark(),detail.getIntegral(),detail.getAddedTime());
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
 
 	public int updateGoodsDetail(Connection con, GoodsDetail detail)throws Exception{
 		StringBuffer sb = new StringBuffer("update goods_detail set ");
 		
 		if (StringUtils.isNotBlank(detail.getGoodsName())) {
 			sb.append("goods_name='"+ detail.getGoodsName() +"',");
 		}
 		if (detail.getGoodsPricel() != null) {
 			sb.append("goods_price="+ detail.getGoodsPricel() +",");
 		}
 		if (detail.getTypeId() > 0) {
 			sb.append("type_id="+ detail.getTypeId() +",");
 		}
 		if (detail.getStockSize() > 0) {
 			sb.append("stock_size="+ detail.getStockSize() +",");
 		}
 		if (detail.getCompany() > 0) {
 			sb.append("company="+ detail.getCompany()  +",");
 		}
 		if (detail.getIntegral() > 0) {
 			sb.append("integral="+ detail.getIntegral() +",");
 		}
 		if (StringUtils.isNotBlank(detail.getDetailDes())) {
 			sb.append("detail_des='"+ detail.getDetailDes() +"',");
 		}
 		if (detail.getCarryFare() > 0) {
 			sb.append("carry_fare="+ detail.getCarryFare() +",");
 		}
 		if (detail.getAddedTime() != null) {
 			sb.append("added_time='"+ detail.getAddedTime() +"',");
 		}
 		
 		String sql = sb.toString();
 		if (sql.endsWith(",")) {
 			sql = sql.substring(0, sql.length()-1);
 		}
 		
 		sql += " where goods_id = "+ detail.getGoodsId();
 		log.info("updateGoodsDetail sql="+ sql);
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
 
 	public int delete(Connection con, String goodsId) throws SQLException {
		String sql = "delete from goods_detail where goods_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, goodsId);
		return pstmt.executeUpdate();
	}

 
	public ResultSet goodsDetailList(Connection con, PageBean pageBean, GoodsDetail goodsDetail) throws Exception {
		StringBuffer sb = new StringBuffer(
				"select g.goods_id, g.goods_name, g.goods_price, g.type_id, t.type_name, g.stock_size, g.company, g.simple_des,"
						+ "g.detail_des, g.master_img, g.slave_img, g.carry_fare, g.carry_fare_des, g.keyword, g.remark, g.state, g.create_time, g.update_time "
						+ "from goods_detail g left join goods_type t on g.type_id = t.type_id where g.state = 1");

		if (StringUtil.isNotEmpty(goodsDetail.getGoodsName())) {
			sb.append(" and goods_name like '%" + goodsDetail.getGoodsName() + "%'");
		}

		sb.append(" ORDER BY create_time DESC");

		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getRows());
		}
		log.info(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}

	public int goodsDetailCount(Connection con, GoodsDetail goodsDetail) throws Exception {
		StringBuffer sb = new StringBuffer(
				"select count(1) as total from goods_detail g left join goods_type t on g.type_id = t.type_id where g.state = 1 ");
		if (StringUtil.isNotEmpty(goodsDetail.getGoodsName())) {
			sb.append(" where goods_name like '%" + goodsDetail.getGoodsName() + "%'");
		}
		log.info(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}
	 
}
