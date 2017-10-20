package com.snail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.snail.dao.base.BaseDao;
import com.snail.model.FeedBack;
import com.snail.model.PageBean;
import com.snail.util.DbUtil;
import com.snail.util.StringUtil;

public class FeedBackDao extends BaseDao {
	 
	 public boolean addFeedBack(FeedBack feedBack) {
	        boolean result = false;
	        Connection connection = null;
	        try {
	            connection = DbUtil.getConnection();
	            String sql = "INSERT INTO feedback (user_id,feed_content,state) VALUES (?,?,?)";
	            DbUtil.beginTx(connection);
	            int isSuccess = qR.update(connection, sql, feedBack.getUserId(),feedBack.getFeedContent(),feedBack.getState());
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
	 
	
	public int feedBackAdd(Connection con,FeedBack feedBack)throws Exception{
		String sql="insert into feedback (user_id,feed_content) values(?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, feedBack.getUserId());
		pstmt.setString(2, feedBack.getFeedContent());
		return pstmt.executeUpdate();
	}

	
	public int feedBackUpdate(Connection con,FeedBack feedBack)throws Exception{
		String sql="update feedback set feed_content=? where feed_id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, feedBack.getFeedContent());
		pstmt.setInt(2, feedBack.getFeedId());
		return pstmt.executeUpdate();
	}
	
	public ResultSet feedBackList(Connection con,PageBean pageBean,FeedBack feedBack)throws Exception{
		StringBuffer sb=new StringBuffer("select feed_id,feed_content, (case when state = 1 then '正常' else '删除'  end) as state,create_time,update_time from feedback f where  f.state=1 ");
		
		if(StringUtil.isNotEmpty(feedBack.getFeedContent())){
			sb.append(" and feed_content like '%"+feedBack.getFeedContent()+"%'");
		}
		
		sb.append(" ORDER BY create_time DESC");
		
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	
	
	public int feedBackCount(Connection con,FeedBack feedBack)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from feedback ");
		if(StringUtil.isNotEmpty(feedBack.getFeedContent())){
			sb.append(" and feed_content like '%"+feedBack.getFeedContent()+"%'");
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
