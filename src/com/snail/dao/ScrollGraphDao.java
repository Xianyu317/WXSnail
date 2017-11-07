package com.snail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.snail.dao.base.BaseDao;
import com.snail.model.PageBean;
import com.snail.model.ScrollGraph;
import com.snail.util.DbUtil;
import com.snail.util.StringUtil;

public class ScrollGraphDao extends BaseDao {

	/**
	 * CREATE TABLE `scroll_graph` ( `graph_id` smallint(6) NOT NULL
	 * AUTO_INCREMENT, `graph_path` varchar(100) DEFAULT '' COMMENT '图片路径',
	 * `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序', `remark`
	 * varchar(100) DEFAULT NULL COMMENT '备注', `state` tinyint(1) NOT NULL
	 * DEFAULT '1' COMMENT '状态:0隐藏，1显示', `create_time` timestamp NOT NULL
	 * DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', `update_time` timestamp NOT
	 * NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT
	 * '修改时间', PRIMARY KEY (`graph_id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8
	 * COMMENT='轮播图';
	 * 
	 * @param scrollGraph
	 * @return
	 */
	public boolean addScrollGraph(ScrollGraph graph) {
		boolean result = false;
		Connection connection = null;

		try {
			connection = DbUtil.getConnection();
			String sql = "INSERT INTO scroll_graph (graph_path,order_num,remark,state,create_time,update_time) VALUES (?,?,?,?,now(),now())";
			DbUtil.beginTx(connection);
			int isSuccess = qR.update(connection, sql, graph.getGraphPath(), graph.getOrderNum(), graph.getRemark(),
					graph.getState());
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

	public ResultSet scrollGraphList(Connection con, PageBean pageBean, ScrollGraph scrollGraph,String state) throws Exception {
//		StringBuffer sb = new StringBuffer(
//				"select goods_id,goods_name,goods_price,type_id,stock_size,company,simple_des,detail_des,master_img,slave_img,carry_fare,carry_fare_des,keyword,remark, (case when state = 1 then '正常' else '删除'  end) as state,create_time,update_time from scroll_graph g where  g.state=1 ");
//
//		if (StringUtil.isNotEmpty(scrollGraph.getRemark())) {
//			sb.append(" and remark like '%" + scrollGraph.getRemark() + "%'");
//		}
		StringBuffer sb = new StringBuffer(
				"select graph_id,graph_path,order_num,remark,state,(case state when 1 then '有效' else '无效' end) statestr,create_time from scroll_graph g where  1=1 ");
		if(StringUtil.isNotEmpty(state)){
			sb.append(" and g.state=" + state);
		}else{
			sb.append(" and g.state=1 ");
		}
		
		sb.append(" ORDER BY order_num ASC");

		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getRows());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}

	public int scrollGraphCount(Connection con, ScrollGraph scrollGraph) throws Exception {
		StringBuffer sb = new StringBuffer("select count(*) as total from scroll_graph where state=1 ");
		if (StringUtil.isNotEmpty(scrollGraph.getRemark())) {
			sb.append(" and remark like '%" + scrollGraph.getRemark() + "%'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}
	
	public int delete(Connection con, String graphIds) throws SQLException {
		String sql = "delete from scroll_graph where graph_id in (" + graphIds + ")";
		PreparedStatement pstmt = con.prepareStatement(sql);
//		String sql = "delete from scroll_graph where graph_id = ?";
//		PreparedStatement pstmt = con.prepareStatement(sql);
//		pstmt.setString(1, graphId);
		return pstmt.executeUpdate();
	}
}
