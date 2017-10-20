package com.snail.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbUtil {
    private static Logger logger = (Logger) LogManager.getLogger(DbUtil.class);  
	//处理数据库事务的
			//提交事务
			public static void commit(Connection connection){
				if(connection != null){
					try {
						connection.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						logger.error("Transaction commit failed!",e.getMessage());
					}
				}
			}
			
			//回滚事务
			public static void rollback(Connection connection){
				if(connection != null){
					try {
						connection.rollback();
					} catch (SQLException e) {
						e.printStackTrace();
						logger.error("Transaction rollback failed!",e.getMessage());
					}
				}
			}
			
			//开始事务
			public static void beginTx(Connection connection){
				if(connection != null){
					try {
						connection.setAutoCommit(false);
					} catch (SQLException e) {
						e.printStackTrace();
						logger.error("Start transaction failed!",e.getMessage());
					}
				}
			}
			
			private static DataSource dataSource = null;

			//数据库连接池应只被初始化一次. 
			static{
				dataSource = new ComboPooledDataSource("c3p0");
				logger.info("Database connection pool creation success!");
			}
			
			public static Connection getConnection() throws SQLException {
				return dataSource.getConnection();
			}
			
			

			public static void releaseDB(ResultSet resultSet, Statement statement,
					Connection connection) {

				if (resultSet != null) {
					try {
						resultSet.close();
					} catch (SQLException e) {
					    logger.error("Close result set failed!",e.getMessage());
						e.printStackTrace();
					}
				}

				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException e) {
					    logger.error("Closing statement failed!",e.getMessage());
						e.printStackTrace();
					}
				}

				if (connection != null) {
					try {
						//数据库连接池的 Connection 对象进行 close 时
						//并不是真的进行关闭, 而是把该数据库连接会归还到数据库连接池中. 
						connection.close();
					} catch (SQLException e) {
					    logger.error("The release of the database connection to the connection pool failed!",e.getMessage());
						e.printStackTrace();
					}
				}
			}

}
