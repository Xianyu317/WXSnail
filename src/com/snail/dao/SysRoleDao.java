package com.snail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.snail.dao.base.BaseDao;
import com.snail.model.PageBean;
import com.snail.model.SysRole;
import com.snail.util.DbUtil;
import com.snail.util.StringUtil;


public class SysRoleDao extends BaseDao {
	

	 public String getRoleNameById(int id){
			String sql="select roleId,roleName,authIds,roleDescription from sys_role where roleId=?";
			Connection connection = null;
			SysRole s = null;
			try {
				connection = DbUtil.getConnection();
				DbUtil.beginTx(connection);
				s = qR.query(connection,sql,new BeanHandler<SysRole>(SysRole.class),id);
				return s.getRoleName();
			} catch (Exception e) {
				DbUtil.rollback(connection);
				e.printStackTrace();
			} finally{
				DbUtil.releaseDB(null, null, connection);	
			}
			return s.getRoleName();
		}
	 
	 
	 

	public String getRoleNameById(Connection con,int id)throws Exception{
		String roleName=null;
		String sql="select roleName from sys_role where roleId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1,id);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			roleName=rs.getString("roleName");
		}
		return roleName;
	}
	
	public String getAuthIdsById(Connection con,int id)throws Exception{
		String authIds=null;
		String sql="select authIds from sys_role where roleId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1,id);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			authIds=rs.getString("authIds");
		}
		return authIds;
	}
	
	public ResultSet roleList(Connection con,PageBean pageBean,SysRole role)throws Exception{
		StringBuffer sb=new StringBuffer("select * from sys_role");
		if(StringUtil.isNotEmpty(role.getRoleName())){
			sb.append(" and roleName like '%"+role.getRoleName()+"%'");
		}
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}
	
	public int roleCount(Connection con,SysRole role)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from sys_role ");
		if(StringUtil.isNotEmpty(role.getRoleName())){
			sb.append(" and roleName like '%"+role.getRoleName()+"%'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public int roleDelete(Connection con,String delIds)throws Exception{
		String sql="delete from sys_role where roleId in ("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int roleAdd(Connection con,SysRole role)throws Exception{
		String sql="insert into sys_role values(null,?,'',?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, role.getRoleName());
		pstmt.setString(2, role.getRoleDescription());
		return pstmt.executeUpdate();
	}
	
	public int roleUpdate(Connection con,SysRole role)throws Exception{
		String sql="update sys_role set roleName=?,roleDescription=? where roleId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, role.getRoleName());
		pstmt.setString(2, role.getRoleDescription());
		pstmt.setInt(3, role.getRoleId());
		return pstmt.executeUpdate();
	}
	
	public int roleAuthIdsUpdate(Connection con,SysRole role)throws Exception{
		String sql="update sys_role set authIds=? where roleId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, role.getAuthIds());
		pstmt.setInt(2, role.getRoleId());
		return pstmt.executeUpdate();
	}
}
