package com.ai.am.dao.common;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao {
	 public List<Object>  queryBySql(String sql) throws SQLException;
	   public void  updateBySQL(String SQL);
}
