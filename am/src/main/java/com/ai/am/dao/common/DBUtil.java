package com.ai.am.dao.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.ai.process.jta.DatabaseContextHolder;
import com.ai.process.jta.DynamicDataSource;
import com.ai.process.jta.DynamicDataSourceListener;

public class DBUtil    {
	
   private  static final  String  DATASOURCE_CSHP03 = "CSHP03";
   public final static Logger log = LoggerFactory.getLogger(DBUtil.class);
   
	
   private static DataSource dataSource;

   
   public DataSource getDataSource() {
	return dataSource;
}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		Connection con = null;
		   if(dataSource!=null){
			   con =  dataSource.getConnection();
		   }
		   return con;
	   }
	   
	
	/**
	 * 开启事务
	 * @param con
	 */
	public static void commit(Connection con){
		
		if(con!=null){
			
			try {
				if(!con.getAutoCommit()){
					con.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else{
			log.info("无法获取connection");
		}
	}
	
	
	/**
	 * 事务回滚
	 * @param con
	 */
	public static void rollback(Connection con){
	if(con!=null){
			
			try {
				if(!con.getAutoCommit()){
					con.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else{
			log.info("无法获取connection");
		}
	}
	
	
	
	/**
	 * 关闭数据库连接
	 * @param con
	 * @param st
	 * @param rs
	 * @return 
	 */
	public static  void closeConnection(Connection con, Statement st , ResultSet rs){
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(st!=null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
	}

	

}
