package com.snail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.snail.dao.base.BaseDao;
import com.snail.model.PageBean;
import com.snail.model.SysUser;
import com.snail.model.UserPoint;
import com.snail.util.DbUtil;
import com.snail.util.StringUtil;

public class SysUserDao extends BaseDao {
	 QueryRunner qR = new QueryRunner();
	 
	 /**
	  * CREATE TABLE `sys_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `userType` tinyint(4) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  `userDescription` varchar(200) DEFAULT NULL,
  
  userId,userName,password,userType,roleId,userDescription
	  * @param userPoint
	  * @return
	  */
	 
	 public boolean addUserPoint(UserPoint userPoint) {
	        boolean result = false;
	        Connection connection = null;

	        try {
	            connection = DbUtil.getConnection();
	            String sql = "INSERT INTO sys_user (userName,password,userType,roleId,userDescription) VALUES (?,?,?,?,?)";
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


	 public SysUser login (String userName,String userPwd){
			Connection connection = null;
			SysUser u = null;
			try {
				connection = DbUtil.getConnection();
				String sql = "SELECT userId,userName,password,userType,roleId,userDescription FROM sys_user WHERE userName = ? and password = ?";
				DbUtil.beginTx(connection);
				u = qR.query(connection,sql,new BeanHandler<SysUser>(SysUser.class),userName,userPwd);
			} catch (Exception e) {
				DbUtil.rollback(connection);
				e.printStackTrace();
			} finally{
				DbUtil.releaseDB(null, null, connection);
				return u;
			}
		}
	 
	 
	 
	public SysUser login(Connection con, SysUser user) throws Exception {
		SysUser resultUser = null;
		String sql = "select * from sys_user where userName=? and password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			resultUser = new SysUser();
			resultUser.setUserId(rs.getInt("userId"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setRoleId(rs.getInt("roleId"));
		}
		return resultUser;
	}

	public int modifyPassword(Connection con, SysUser user) throws Exception {
		String sql = "update sys_user set password=? where userId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getPassword());
		pstmt.setInt(2, user.getUserId());
		return pstmt.executeUpdate();
	}

	public ResultSet userList(Connection con, PageBean pageBean, SysUser user)
			throws Exception {
		StringBuffer sb = new StringBuffer(
				"select * from sys_user u ,sys_role r where u.roleId=r.roleId and u.userType!=1 ");
		if (StringUtil.isNotEmpty(user.getUserName())) {
			sb.append(" and u.userName like '%" + user.getUserName() + "%'");
		}
		if (user.getRoleId() != -1) {
			sb.append(" and u.roleId=" + user.getRoleId());
		}
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getRows());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}

	public int userCount(Connection con, SysUser user) throws Exception {
		StringBuffer sb = new StringBuffer(
				"select count(*) as total from sys_user u ,sys_role r where u.roleId=r.roleId and u.userType!=1 ");
		if (StringUtil.isNotEmpty(user.getUserName())) {
			sb.append(" and u.userName like '%" + user.getUserName() + "%'");
		}
		if (user.getRoleId() != -1) {
			sb.append(" and u.roleId=" + user.getRoleId());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public int userAdd(Connection con, SysUser user) throws Exception {
		String sql = "insert into sys_user values(null,?,?,2,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.setInt(3, user.getRoleId());
		pstmt.setString(4, user.getUserDescription());
		return pstmt.executeUpdate();
	}

	public int userUpdate(Connection con, SysUser user) throws Exception {
		String sql = "update sys_user set userName=?,password=?,roleId=?,userDescription=? where userId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.setInt(3, user.getRoleId());
		pstmt.setString(4, user.getUserDescription());
		pstmt.setInt(5, user.getUserId());
		return pstmt.executeUpdate();
	}

	public boolean existUserWithUserName(Connection con, String userName)
			throws Exception {
		String sql = "select * from sys_user where userName=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		return pstmt.executeQuery().next();
	}

	public int userDelete(Connection con, String delIds) throws Exception {
		String sql = "delete from sys_user where userId in (" + delIds + ")";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	public boolean existUserWithRoleId(Connection con, String roleId)
			throws Exception {
		String sql = "select * from sys_user where roleId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, roleId);
		return pstmt.executeQuery().next();
	}
}
