package com.snail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.snail.dao.base.BaseDao;
import com.snail.model.GoodsType;
import com.snail.model.PageBean;
import com.snail.util.DbUtil;
import com.snail.util.StringUtil;

public class GoodsTypeDao extends BaseDao {
	
	 /**
	  * CREATE TABLE `goods_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `type_topid` int(11) DEFAULT NULL COMMENT '父ID',
  `is_parent` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否总类别:0子类，1父类',
  `type_name` varchar(100) DEFAULT NULL COMMENT '类别名',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0禁用，1可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类型';

type_id,type_topid,is_parent,type_name,remark,state,create_time,update_time
	  * @param goodsType
	  * @return
	  */
	 public boolean addGoodsType(GoodsType goodsType) {
	        boolean result = false;
	        Connection connection = null;

	        try {
	            connection = DbUtil.getConnection();
	            String sql = "INSERT INTO goods_type (type_topid,is_parent,type_name,remark,state) VALUES (?,?,?,?,?)";
	            DbUtil.beginTx(connection);
	            int isSuccess = qR.update(connection, sql, goodsType.getTypeTopid(),goodsType.getIsParent(),goodsType.getTypeName(),goodsType.getRemak(),goodsType.getState());
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
	 
	 
	 
	 public ResultSet goodsTypeList(Connection con,PageBean pageBean,GoodsType goodsType)throws Exception{
			StringBuffer sb=new StringBuffer("select type_id,type_topid,is_parent,type_name,remark, (case when state = 1 then '正常' else '删除'  end) as state,create_time,update_time from goods_type g where  g.state=1 ");
			
			if(StringUtil.isNotEmpty(goodsType.getTypeName())){
				sb.append(" and type_name like '%"+goodsType.getTypeName()+"%'");
			}
			
			if(pageBean!=null){
				sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
			}
			PreparedStatement pstmt=con.prepareStatement(sb.toString());
			return pstmt.executeQuery();
		}
		
		
		
		public int goodsTypeCount(Connection con,GoodsType goodsType)throws Exception{
			StringBuffer sb=new StringBuffer("select count(*) as total from goods_type ");
			if(StringUtil.isNotEmpty(goodsType.getTypeName())){
				sb.append(" and type_name like '%"+goodsType.getTypeName()+"%'");
			}
			PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("total");
			}else{
				return 0;
			}
		}
		
}
