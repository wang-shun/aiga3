package com.ai.process.jta.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ai.aiga.service.base.BaseService;
import com.ai.process.jta.DatabaseContextHolder;

public class AbstractJatService extends BaseService {
	
	@Autowired 
	@Qualifier("dynamicDatasource")
	private DataSource dataSource;
	
	private DataSource getDataSource() {
		return this.dataSource;
	}
	
	protected Connection getConnection() throws SQLException {
		Connection ret = null;
		if (getDataSource() != null) {
			ret = getDataSource().getConnection();
		}
		return ret;
	}

	protected void closeConnection(Connection c) throws SQLException {
		if (c != null)
			c.close();
	}
	
	protected Connection getConnection(String dbType) throws SQLException {
		DatabaseContextHolder.setDBType(dbType);
		return getConnection();
	}
	
}
