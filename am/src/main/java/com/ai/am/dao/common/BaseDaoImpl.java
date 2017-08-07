package com.ai.am.dao.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.am.exception.BusinessException;

public class BaseDaoImpl implements BaseDao {
	
	   public final static Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	   
	   /**
	    * 执行查询，不带分页
	    * @param sql
	    * @return
	    * @throws SQLException
	    */
	   public List<Object>  queryBySql(String sql) throws SQLException{
		   //获取数据库连接
		   Connection con =    DBUtil.getConnection();
		   List<Object> resultList = new ArrayList<Object>();
		   if(con!=null){
				   Statement st = con.createStatement();
				   ResultSet  result =   st.executeQuery(sql);
				   ResultSetMetaData rsmd =  result.getMetaData();
				   while(result.next()){
					   List<Object> list = new ArrayList<Object>();
						   for(int i=1;i<=rsmd.getColumnCount();i++){
						    list.add(result.getObject(i));	   
					   }
				   resultList.add(list);
			  }  
		  }else{
			  log.info("BaseDaoImpl中conncetion为null");
		  }
		   return resultList;
	   }
	   
	   
	   /**
	    * 执行更新操作
	    * @param SQL
	    */
	   public void  updateBySQL(String SQL) {
		   if(SQL==null||"".equals(SQL)){
			   BusinessException.throwBusinessException("updateBySQL中SQL为nulll");
		   }
		   
		   Connection con=null;
			 int count = 0;
		   
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			 if(con!=null){
				 count=   con.createStatement().executeUpdate(SQL);
				 DBUtil.commit(con);;
		   }
		} catch (SQLException e) {
			DBUtil.rollback(con);
			e.printStackTrace();
		}
		log.info("成功执行"+count+"行");
	   }
}
